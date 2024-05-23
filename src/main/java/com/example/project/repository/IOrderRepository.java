package com.example.project.repository;

import com.example.project.model.entity.OrderStatus;
import com.example.project.model.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> getOrdersByUserId(Long id);

    Orders getOrdersBySerialNumber(String serialNumber);

    Page<Orders> findAllByOderStatus(OrderStatus orderStatus, Pageable pageable);

    List<Orders> findAllByOderStatusAndUserId(OrderStatus orderStatus,Long id);

}
