package com.example.project.model.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountEditPassword {
    private String oldPassword;
    private String newPassWord;
    private String confirmPassWord;
}
