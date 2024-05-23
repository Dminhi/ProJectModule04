package com.example.project.model.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailDTO {
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
}
