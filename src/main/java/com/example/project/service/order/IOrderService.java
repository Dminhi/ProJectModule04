package com.example.project.service.order;

import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.entity.OrderStatus;
import com.example.project.model.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderService{
    List<OrderResponse> getOrderByUserId() throws NotFoundException, DataNotFound;

    OrderResponse getOrderResponseBySerialNumber(String serialNumber) throws NotFoundException, DataNotFound;

    Page<OrderResponse> getOrderResponseByOrderStatus(String orderStatus, Pageable pageable) throws NotFoundException, DataNotFound;

    OrderResponse getOrderByOrderId(Long id) throws NotFoundException, DataNotFound;

    OrderResponse updateOrderStatus(Long id, OrderStatus status) throws NotFoundException;

    Page<OrderResponse> getAllOrder(Pageable pageable) throws NotFoundException;
    Page<OrderResponse> getOrderResponseByOrderStatusByUser(String orderStatus, Pageable pageable) throws NotFoundException;





}
