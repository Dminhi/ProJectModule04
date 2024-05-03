package com.example.project.controller.admin;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.entity.OrderStatus;
import com.example.project.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/orders")
public class OrdersController {
    @Autowired
    private IOrderService orderService;


    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderByOrderStatus(@PathVariable Long orderId) throws NotFoundException {
        OrderResponse orderResponse = orderService.getOrderByOrderId(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);}

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestParam String orderStatus ) throws NotFoundException {
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId,OrderStatus.valueOf(orderStatus));
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
