package com.example.project.model.dto.response;

import com.example.project.model.entity.OrderStatus;
import com.example.project.model.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private Double totalPrice;
    private String serialNumber;
    private OrderStatus orderStatus;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt;
    private List<OrderDetailDTO> orderDetail;
}
