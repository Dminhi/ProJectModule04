package com.example.project.service.shoppingcart;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.shoppingcart.CheckOutRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartEditRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartRequest;
import com.example.project.model.dto.response.ShoppingCartResponse;
import com.example.project.model.entity.*;
import com.example.project.repository.*;
import com.example.project.securiry.principle.UserDetailsCustom;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
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
    public ShoppingCart save(ShoppingCartRequest shoppingCartRequest) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByProductIdAndUserId(shoppingCartRequest.getProductId(), userDetailsCustom.getId());
        if(optionalShoppingCart.isPresent()){
            ShoppingCart shoppingCart = optionalShoppingCart.get();
            shoppingCart.setOrderQuantity(shoppingCart.getOrderQuantity()+shoppingCartRequest.getOrderQuantity());
            return shoppingCartRepository.save(shoppingCart);
        }
        else {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .orderQuantity(shoppingCartRequest.getOrderQuantity())
                .user(userRepository.findById(userDetailsCustom.getId()).orElseThrow(()->new NotFoundException("user not found")))
                .product(productRepository.findById(shoppingCartRequest.getProductId()).orElseThrow(()->new NotFoundException("product not found")))
        .build();
        return shoppingCartRepository.save(shoppingCart);}
    }

    @Override
    public ShoppingCart save(ShoppingCartEditRequest shoppingCartEditRequest,Long id) throws NotFoundException {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(()-> new NotFoundException("shopping cart not found"));
        shoppingCart.setOrderQuantity(shoppingCartEditRequest.getOrderQuantity());
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteShoppingCartById(Long id) {
            shoppingCartRepository.deleteById(id);
    }

    @Override
    public void deleteAllShoppingCart() {
        shoppingCartRepository.deleteAll();
    }

    @Override
    @Transactional
    public Orders checkOut(CheckOutRequest checkOutRequest) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        User user = userRepository.findById(userDetailsCustom.getId()).orElseThrow(()-> new NotFoundException("user not found"));
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        for (Long l : checkOutRequest.getShoppingCartId()) {
            shoppingCarts.add(shoppingCartRepository.findById(l).orElseThrow(()->new NotFoundException("shopping cart not found")));
        }
        Double totalPrice = shoppingCarts.stream().map(item -> item.getProduct().getUnitPrice()*item.getOrderQuantity()).reduce( 0.0,(sum, number)->sum+=number);
        List<OrderDetail> orderDetails = new ArrayList<>();
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
        shoppingCarts.forEach(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(newOrder);
            orderDetail.setName(shoppingCart.getProduct().getProductName());
            orderDetail.setUnitPrice(shoppingCart.getProduct().getUnitPrice());
            orderDetail.setProduct(shoppingCart.getProduct());
            orderDetail.setOrderQuantity(shoppingCart.getOrderQuantity());
            orderDetail.setId(new CompositeKey(newOrder.getId(),shoppingCart.getProduct().getId()));
            orderDetailRepository.save(orderDetail);
        });
        for (ShoppingCart shoppingCart : shoppingCarts) {
           Product product = shoppingCart.getProduct();
            product.setStockQuantity(product.getStockQuantity()-shoppingCart.getOrderQuantity());
           productRepository.save(product);
           shoppingCartRepository.deleteById(shoppingCart.getId());
        }
        return newOrder;
    }
}
