package com.example.project.model.dto.request.product;

import com.example.project.model.entity.Category;
import com.example.project.validator.NameExist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductEditRequest {
    private String productName;
    private String description;
    private Double unitPrice;
    private Integer stockQuantity;
    private Long category;
    private boolean status;
}
