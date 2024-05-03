package com.example.project.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WishListResponse {
    private String productName;
    private double unitPrice;
}
