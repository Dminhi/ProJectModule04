package com.example.project.model.dto.request.product;

import com.example.project.model.entity.Product;
import com.example.project.validator.NameExist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductRequest {
    @NameExist(entityClass = Product.class,existName = "productName")
    private String productName;
    private String description;
    @NotNull(message = "Unit Price not null")
    private Double unitPrice;
    @NotNull(message = "Stock Quantity not null")
    private Integer stockQuantity;
    @NotNull(message = "categoryId not null")
    private Long categoryId;
}
