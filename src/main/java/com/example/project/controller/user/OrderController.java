package com.example.project.controller.user;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.dto.response.ShoppingCartResponse;
import com.example.project.model.entity.Orders;
import com.example.project.repository.IOrderRepository;
import com.example.project.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/user/history")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<OrderResponse>> findAll() throws NotFoundException {
        List<OrderResponse> orderResponses = orderService.getOrderByUserId();
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<OrderResponse> getDetailOrderBySerialNumber(@PathVariable String serialNumber) throws NotFoundException {
        OrderResponse orderResponse = orderService.getOrderResponseBySerialNumber(serialNumber);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("order/{orderStatus}")
    public ResponseEntity<List<OrderResponse>> getOrderByOrderStatus(@PathVariable String orderStatus) throws NotFoundException {
        List<OrderResponse> orderResponse = orderService.getOrderResponseByOrderStatus(orderStatus);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);}
}
