package com.example.project.model.dto.request.account;

import com.example.project.model.entity.User;
import com.example.project.validator.NameExist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountEditRequest {
    @NotEmpty(message = "Thuộc tính không được để trống.")
    @Size(max = 100, message = "Độ dài tối đa là 100 ký tự.")
    private String fullName;
    @Email(message = "Email không hợp lệ.")
    @NameExist(entityClass = User.class,existName = "email")
    private String email;
    private String avatar;
    private String phone;
}
