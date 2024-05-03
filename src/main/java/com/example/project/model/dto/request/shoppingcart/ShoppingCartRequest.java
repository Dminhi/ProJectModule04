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
    @Min(value = 0, message = "Giá trị phải lớn hơn hoặc bằng 0.")
    private int orderQuantity;
    @NotNull
    private Long productId;
}
