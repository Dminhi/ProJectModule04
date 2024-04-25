package com.example.project.model.dto.request;

import com.example.project.model.entity.User;
import com.example.project.validator.NameExist;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FormRegister {
    @Size(min = 6, max = 100, message = "Độ dài phải từ 6 đến 100 ký tự.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Không cho phép ký tự đặc biệt.")
    @NameExist(entityClass = User.class,existName = "username")
    private String username;
    @NotEmpty(message = "Thuộc tính không được để trống.")
    @Size(max = 100, message = "Độ dài tối đa là 100 ký tự.")
    private String fullName;
    @Email(message = "Email không hợp lệ.")
    @NameExist(entityClass = User.class,existName = "email")
    private String email;
    private String password;
    private Set<String> roles;
}
