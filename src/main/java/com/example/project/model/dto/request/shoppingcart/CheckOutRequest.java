package com.example.project.model.dto.request.shoppingcart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckOutRequest {
    private String note;
    @NotNull(message = "receiveName not be null")
    private String receiveName;
    @NotNull(message = "receiveName not be null")
    private String receiveAddress;
    @NotNull(message = "receiveName not be null")
    private String receivePhone;
    @NotNull(message = "receiveName not be null")
    List<Long> shoppingCartId;
}
