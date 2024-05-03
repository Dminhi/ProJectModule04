package com.example.project.model.dto.response;

import com.example.project.model.entity.OrderStatus;
import com.example.project.model.entity.Product;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private List<String> productName;
    private Double totalPrice;
    private String serialNumber;
    private OrderStatus orderStatus;
    private Date createdAt;
}
