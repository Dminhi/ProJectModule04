package com.example.project.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private String productName;
    private Integer stockQuantity;
    private Double unitPrice;
    private String image;
    private String categoryName;
}
