package com.example.project.repository;

import com.example.project.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findAllByOrdersOrderId(Long id);
}

