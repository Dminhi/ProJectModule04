package com.example.project.model.dto.response;

import com.example.project.model.entity.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private Integer stockQuantity;
    private Double unitPrice;
    private String image;
    private String categoryName;
}
