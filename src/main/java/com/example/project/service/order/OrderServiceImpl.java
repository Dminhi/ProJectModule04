package com.example.project.service.order;

import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.OrderDetailDTO;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.entity.OrderDetail;
import com.example.project.model.entity.OrderStatus;
import com.example.project.model.entity.Orders;
import com.example.project.repository.IOrderDetailRepository;
import com.example.project.repository.IOrderRepository;
import com.example.project.securiry.principle.UserDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    IOrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderResponse> getOrderByUserId() throws NotFoundException, DataNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        List<Orders> orders = orderRepository.getOrdersByUserId(userDetailsCustom.getId());
        if(orders.isEmpty()){
            throw new DataNotFound("Order not found");
        }

        return orders.stream().map(order->OrderResponse.builder()
                .orderStatus(order.getOderStatus())
                .serialNumber(order.getSerialNumber())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .orderDetail(orderDetailRepository.findAllByOrdersOrderId(
                        order.getOrderId()).stream()
                        .map(OrderServiceImpl::toOrderDetailDTO).toList()
                )
                .build()).toList();
    }

    @Override
    public OrderResponse getOrderResponseBySerialNumber(String serialNumber) throws NotFoundException, DataNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
       Orders orders = orderRepository.getOrdersBySerialNumber(serialNumber);
       if (orders == null){
           throw new DataNotFound("order not found");
       }
       if(!orders.getUser().getId().equals(userDetailsCustom.getId())){
           throw new AccessDeniedException("unauthorized");
       }
       OrderResponse orderResponse = OrderResponse.builder()
               .orderStatus(orders.getOderStatus())
               .createdAt(orders.getCreatedAt())
               .serialNumber(serialNumber)
               .totalPrice(orders.getTotalPrice())
               .orderDetail(orderDetailRepository.findAllByOrdersOrderId(orders.getOrderId()).stream().map(OrderServiceImpl::toOrderDetailDTO).toList())
               .build();
        return orderResponse;
    }

    @Override
    public Page<OrderResponse> getOrderResponseByOrderStatus(String orderStatus, Pageable pageable) throws NotFoundException, DataNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        try{
            OrderStatus ordersStatus = OrderStatus.valueOf(orderStatus);
            Page<Orders> orders = orderRepository.findAllByOderStatus(ordersStatus,pageable);
            if(orders.isEmpty()){throw new DataNotFound("order not found");}
            List<OrderResponse> orderResponses = orders.stream().map(order->OrderResponse.builder()
                    .orderStatus(order.getOderStatus())
                    .serialNumber(order.getSerialNumber())
                    .totalPrice(order.getTotalPrice())
                    .createdAt(order.getCreatedAt())
                    .orderDetail(orderDetailRepository.findAllByOrdersOrderId(
                                    order.getOrderId()).stream()
                            .map(OrderServiceImpl::toOrderDetailDTO).toList()
                    )
                    .build()).toList();
             return new PageImpl<>(orderResponses,pageable,(long)orderResponses.size());
        } catch (Exception e){
                throw new DataNotFound("order not found");
        }
    }

    @Override
    public OrderResponse getOrderByOrderId(Long id) throws NotFoundException, DataNotFound {
        Orders orders = orderRepository.findById(id).orElseThrow(()->new NotFoundException("order not found"));
        if (orders == null){
            throw new DataNotFound("order not found");
        }
        return OrderResponse.builder()
                .orderStatus(orders.getOderStatus())
                .createdAt(orders.getCreatedAt())
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .orderDetail(orderDetailRepository.findAllByOrdersOrderId(orders.getOrderId()).stream().map(OrderServiceImpl::toOrderDetailDTO).toList())
                .build();
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) throws NotFoundException {
        Orders orders = orderRepository.findById(id).orElseThrow(()->new NotFoundException("orders not found"));
        orders.setOderStatus(status);
        orderRepository.save(orders);
        return OrderResponse.builder()
                .orderStatus(orders.getOderStatus())
                .createdAt(orders.getCreatedAt())
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .orderDetail(orderDetailRepository.findAllByOrdersOrderId(orders.getOrderId()).stream().map(OrderServiceImpl::toOrderDetailDTO).toList())
                .build();
    }

    @Override
    public Page<OrderResponse> getAllOrder(Pageable pageable) throws NotFoundException {
        try{
            Page<Orders> orders = orderRepository.findAll(pageable);
            List<OrderResponse> orderResponses = orders.stream().map(order->OrderResponse.builder()
                    .orderStatus(order.getOderStatus())
                    .serialNumber(order.getSerialNumber())
                    .totalPrice(order.getTotalPrice())
                    .createdAt(order.getCreatedAt())
                    .orderDetail(orderDetailRepository.findAllByOrdersOrderId(
                                    order.getOrderId()).stream()
                            .map(OrderServiceImpl::toOrderDetailDTO).toList()
                    )
                    .build()).toList();
            return new PageImpl<>(orderResponses,pageable,orderResponses.size());
        } catch (Exception e){
            throw new  NotFoundException("OrderStatus not found");
        }
    }

    @Override
    public Page<OrderResponse> getOrderResponseByOrderStatusByUser(String orderStatus, Pageable pageable) throws NotFoundException {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
            List<Orders> orders = orderRepository.findAllByOderStatusAndUserId(OrderStatus.valueOf(orderStatus),userDetailsCustom.getId())
                    .stream().filter(o->o.getUser().getId().equals(userDetailsCustom.getId())).toList();
             List<OrderResponse> orderResponse = orders.stream().map(order->OrderResponse.builder()
                    .orderStatus(order.getOderStatus())
                    .serialNumber(order.getSerialNumber())
                    .totalPrice(order.getTotalPrice())
                    .createdAt(order.getCreatedAt())
                    .orderDetail(orderDetailRepository.findAllByOrdersOrderId(
                                    order.getOrderId()).stream()
                            .map(OrderServiceImpl::toOrderDetailDTO).toList()
                    )
                    .build()).toList();
             return new PageImpl<>(orderResponse,pageable,orders.size());
        } catch (Exception e){
            throw new  NotFoundException("OrderStatus not found");
        }
    }

    public static OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null; // Nếu orderDetail là null, trả về null để tránh lỗi
        }

        // Tính toán giá tổng dựa trên số lượng và giá đơn vị
        double totalPrice = orderDetail.getOrderQuantity() * orderDetail.getUnitPrice();

        // Sử dụng Builder của OrderDetailDTO để tạo một đối tượng mới
        return OrderDetailDTO.builder()
                .productName(orderDetail.getProduct() != null ? orderDetail.getProduct().getProductName() : orderDetail.getName())
                .quantity(orderDetail.getOrderQuantity())
                .price(orderDetail.getUnitPrice())
                .totalPrice(totalPrice)
                .build();
    }

}
