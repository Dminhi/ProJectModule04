package com.example.project.service.order;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.entity.OrderStatus;
import com.example.project.model.entity.Orders;
import com.example.project.model.entity.User;
import com.example.project.repository.IOrderRepository;
import com.example.project.repository.IUserRepository;
import com.example.project.securiry.principle.UserDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrderServiceImpl implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public List<OrderResponse> getOrderByUserId() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        List<Orders> orders = orderRepository.getOrdersByUserId(userDetailsCustom.getId());
        return orders.stream().map(order->OrderResponse.builder()
                .orderStatus(order.getOderStatus())
                .serialNumber(order.getSerialNumber())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .productName(order.getOrderDetailList().stream().map(orderDetail -> orderDetail.getProduct().getProductName()).toList())
                .build()).toList();
    }

    @Override
    public OrderResponse getOrderResponseBySerialNumber(String serialNumber) throws NotFoundException {
       Orders orders = orderRepository.getOrdersBySerialNumber(serialNumber);
       if (orders == null){
           throw new  NotFoundException("SerialNumber not found");
       }
       OrderResponse orderResponse = OrderResponse.builder()
               .orderStatus(orders.getOderStatus())
               .createdAt(orders.getCreatedAt())
               .serialNumber(serialNumber)
               .totalPrice(orders.getTotalPrice())
               .productName(orders.getOrderDetailList().stream().map(orderDetail -> orderDetail.getProduct().getProductName()).toList())
               .build();
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getOrderResponseByOrderStatus(String orderStatus) throws NotFoundException {
        try{
            List<Orders> orders = orderRepository.getAllByOderStatus(OrderStatus.valueOf(orderStatus));
            return orders.stream().map(order->OrderResponse.builder()
                    .orderStatus(order.getOderStatus())
                    .serialNumber(order.getSerialNumber())
                    .totalPrice(order.getTotalPrice())
                    .createdAt(order.getCreatedAt())
                    .productName(order.getOrderDetailList().stream().map(orderDetail -> orderDetail.getProduct().getProductName()).toList())
                    .build()).toList();
        } catch (Exception e){
                throw new  NotFoundException("OrderStatus not found");
        }
    }

    @Override
    public OrderResponse getOrderByOrderId(Long id) throws NotFoundException {
        Orders orders = orderRepository.getOrdersById(id);
        if (orders == null){
            throw new  NotFoundException("orders not found");
        }
        return OrderResponse.builder()
                .orderStatus(orders.getOderStatus())
                .createdAt(orders.getCreatedAt())
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .productName(orders.getOrderDetailList().stream().map(orderDetail -> orderDetail.getProduct().getProductName()).toList())
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
                .productName(orders.getOrderDetailList().stream().map(orderDetail -> orderDetail.getProduct().getProductName()).toList())
                .build();
    }

    @Override
    public List<OrderResponse> getAllOrder() throws NotFoundException {
        try{
            List<Orders> orders = orderRepository.findAll();
            return orders.stream().map(order->OrderResponse.builder()
                    .orderStatus(order.getOderStatus())
                    .serialNumber(order.getSerialNumber())
                    .totalPrice(order.getTotalPrice())
                    .createdAt(order.getCreatedAt())
                    .productName(order.getOrderDetailList().stream().map(orderDetail -> orderDetail.getProduct().getProductName()).toList())
                    .build()).toList();
        } catch (Exception e){
            throw new  NotFoundException("OrderStatus not found");
        }
    }


}
