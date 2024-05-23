package com.example.project.controller.admin;

import com.example.project.config.ConvertPageToPaginationDTO;
import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.dto.responsewapper.EHttpStatus;
import com.example.project.model.dto.responsewapper.ResponseWapper;
import com.example.project.model.entity.OrderStatus;
import com.example.project.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<?> getOrderByOrderId(@PathVariable Long orderId) throws NotFoundException, DataNotFound {
        OrderResponse orderResponse = orderService.getOrderByOrderId(orderId);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                orderResponse), HttpStatus.OK);}

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestParam String orderStatus ) throws NotFoundException {
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId,OrderStatus.valueOf(orderStatus));
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                orderResponse), HttpStatus.OK);    }

    @GetMapping("/order/{orderStatus}")
    public ResponseEntity<?> findAllOrderByOrderStatus(@PathVariable String orderStatus,@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) throws NotFoundException, DataNotFound {
        Page<OrderResponse> orderResponseo = orderService.getOrderResponseByOrderStatus(orderStatus,pageable);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ConvertPageToPaginationDTO.convertPageToPaginationDTO(orderResponseo)), HttpStatus.OK);
    }
}
