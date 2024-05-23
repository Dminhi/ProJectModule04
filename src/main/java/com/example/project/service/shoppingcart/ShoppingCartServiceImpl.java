package com.example.project.service.shoppingcart;

import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.shoppingcart.CheckOutRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartEditRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartRequest;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.dto.response.ShoppingCartResponse;
import com.example.project.model.entity.*;
import com.example.project.repository.*;
import com.example.project.securiry.principle.UserDetailsCustom;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService{
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public List<ShoppingCartResponse> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUserId(userDetailsCustom.getId());
        List<ShoppingCartResponse> shoppingCartResponses = new ArrayList<>();
        for (int i = 0; i < shoppingCarts.size(); i++) {
            ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
            shoppingCartResponse.setProductName(shoppingCarts.get(i).getProduct().getProductName());
            shoppingCartResponse.setOrderQuantity(shoppingCarts.get(i).getOrderQuantity());
            shoppingCartResponse.setProductPrice(shoppingCarts.get(i).getProduct().getUnitPrice());
            shoppingCartResponse.setProductCategory(shoppingCarts.get(i).getProduct().getCategory().getCategoryName());
            shoppingCartResponses.add(shoppingCartResponse);
        }
        return shoppingCartResponses;
    }

    @Override
    public ShoppingCartResponse save(ShoppingCartRequest shoppingCartRequest) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByProductProductIdAndUserId(shoppingCartRequest.getProductId(), userDetailsCustom.getId());
        if(optionalShoppingCart.isPresent()){
            ShoppingCart shoppingCart = optionalShoppingCart.get();
            shoppingCart.setOrderQuantity(shoppingCart.getOrderQuantity()+shoppingCartRequest.getOrderQuantity());
            return convertToShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
        }
        else {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .orderQuantity(shoppingCartRequest.getOrderQuantity())
                .user(userRepository.findById(userDetailsCustom.getId()).orElseThrow(()->new NotFoundException("user not found")))
                .product(productRepository.findById(shoppingCartRequest.getProductId()).orElseThrow(()->new NotFoundException("product not found")))
        .build();
        return convertToShoppingCartResponse(shoppingCartRepository.save(shoppingCart));}
    }

    @Override
    public ShoppingCartResponse save(ShoppingCartEditRequest shoppingCartEditRequest,Long id) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();

        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(()-> new NotFoundException("shopping cart not found"));
        if(!shoppingCart.getUser().getId().equals(userDetailsCustom.getId())){
            throw new NotFoundException("shoppingCart not found");
        }
        shoppingCart.setOrderQuantity(shoppingCartEditRequest.getOrderQuantity());
        return convertToShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public void deleteShoppingCartById(Long id) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(()->new NotFoundException("shoppingCart not found"));
        if(!shoppingCart.getUser().getId().equals(userDetailsCustom.getId())){
            throw new NotFoundException("shoppingCart not found");
        }
        shoppingCartRepository.deleteById(id);
    }

    @Override
    public void deleteAllShoppingCart() throws DataNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUserId(userDetailsCustom.getId());
        if(shoppingCarts.isEmpty()){
            throw new DataNotFound("shopping cart is empty");
        }
        shoppingCartRepository.deleteShoppingCartByUserId(userDetailsCustom.getId());
    }

    @Override
    public List<ShoppingCartResponse> findAllByUserId() throws DataNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
       List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUserId(userDetailsCustom.getId());
       if(shoppingCarts.isEmpty()){
           throw new DataNotFound("shoppingcart is empty");
       }
        return shoppingCarts.stream().map(this::convertToShoppingCartResponse).toList();
    }

    @Override
    @Transactional
    public Orders checkOut(CheckOutRequest checkOutRequest) throws NotFoundException, DataNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        User user = userRepository.findById(userDetailsCustom.getId()).orElseThrow(()-> new NotFoundException("user not found"));
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUserId(userDetailsCustom.getId());
        Double totalPrice = shoppingCarts.stream().map(item -> item.getProduct().getUnitPrice()*item.getOrderQuantity()).reduce( 0.0,(sum, number)->sum+=number);
        String serialNumber = UUID.randomUUID().toString();
        Orders orders = Orders.builder()
                .receivePhone(checkOutRequest.getReceivePhone())
                .receiveAddress(checkOutRequest.getReceiveAddress())
                .receiveName(checkOutRequest.getReceiveName())
                .serialNumber(serialNumber)
                .note(checkOutRequest.getNote())
                .createdAt(new Date())
                .user(user)
                .totalPrice(totalPrice)
                .oderStatus(OrderStatus.WAITING)
                .build();
        Orders newOrder = orderRepository.save(orders);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(shoppingCart.getProduct().getProductName());
            orderDetail.setUnitPrice(shoppingCart.getProduct().getUnitPrice());
            orderDetail.setProduct(shoppingCart.getProduct());
            orderDetail.setOrders(newOrder);
            orderDetail.setOrderQuantity(shoppingCart.getOrderQuantity());
            orderDetail.setId(new CompositeKey(newOrder.getOrderId(),shoppingCart.getProduct().getProductId()));
            return orderDetail;
        }).toList();
        orderDetailRepository.saveAll(orderDetails);
        for (ShoppingCart shoppingCart : shoppingCarts) {
           Product product = shoppingCart.getProduct();
            product.setStockQuantity(product.getStockQuantity()-shoppingCart.getOrderQuantity());
           productRepository.save(product);
           shoppingCartRepository.deleteById(shoppingCart.getId());
        }
        return newOrder;
    }
    public ShoppingCartResponse convertToShoppingCartResponse(ShoppingCart shoppingCart) {
        // Lấy thông tin từ ShoppingCart
        Long id = shoppingCart.getId();
        int orderQuantity = shoppingCart.getOrderQuantity();

        // Lấy thông tin từ sản phẩm trong giỏ hàng
        String productName = shoppingCart.getProduct().getProductName();
        Double productPrice = shoppingCart.getProduct().getUnitPrice();
        String productCategory = shoppingCart.getProduct().getCategory().getCategoryName();

        // Tạo và trả về một đối tượng ShoppingCartResponse với dữ liệu thu được
        ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse(
                id,
                orderQuantity,
                productName,
                productPrice,
                productCategory
        );
        return shoppingCartResponse;
    }

}
