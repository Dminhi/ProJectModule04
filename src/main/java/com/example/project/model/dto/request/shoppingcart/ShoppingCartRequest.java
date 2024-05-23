package com.example.project.model.dto.request.shoppingcart;

import com.example.project.model.entity.Product;
import com.example.project.model.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ShoppingCartRequest {
    @Min(value = 1, message = "orderQuantity must be more than  0.")
    @NotNull(message = "orderQuantity not be null")
    private int orderQuantity;
    @NotNull(message = "productId not be null")
    private Long productId;
}
