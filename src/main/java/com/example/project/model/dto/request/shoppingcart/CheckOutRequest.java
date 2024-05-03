package com.example.project.model.dto.request.shoppingcart;

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
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    List<Long> shoppingCartId;
}
