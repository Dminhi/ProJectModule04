package com.example.project.model.dto.request.product;

import com.example.project.model.entity.Category;
import com.example.project.validator.NameExist;
import jakarta.validation.constraints.Min;
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
public class ProductEditRequest {
    @NotNull(message = "productName not null")
    private String productName;
    private String description;
    @NotNull(message = "unitPrice not null")
    @Min(value = 1,message = "unitPrice must be more than 0")
    private Double unitPrice;
    @NotNull(message = "stockQuantity not null")
    @Min(value = 1,message = "stockQuantity must be more than 0")
    private Integer stockQuantity;
    @NotNull(message = "category not null")
    private Long category;
    @NotNull(message = "status not null")
    private boolean status;
}
