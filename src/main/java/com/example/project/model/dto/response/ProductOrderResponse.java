package com.example.project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOrderResponse {
    private String productName;
    private Integer productQuantity;
    private Double productPrice;
}
