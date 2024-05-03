package com.example.project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartResponse {
    private int orderQuantity;
    private String productName;
    private Double productPrice;
    private String productCategory;
}
