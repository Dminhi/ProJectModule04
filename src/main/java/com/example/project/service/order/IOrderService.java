package com.example.project.service.order;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.entity.OrderStatus;
import com.example.project.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderService{
    List<OrderResponse> getOrderByUserId() throws NotFoundException;

    OrderResponse getOrderResponseBySerialNumber(String serialNumber) throws NotFoundException;

    List<OrderResponse> getOrderResponseByOrderStatus(String orderStatus) throws NotFoundException;

    OrderResponse getOrderByOrderId(Long id) throws NotFoundException;

    OrderResponse updateOrderStatus(Long id, OrderStatus status) throws NotFoundException;

    List<OrderResponse> getAllOrder() throws NotFoundException;

}
