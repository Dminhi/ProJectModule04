package com.example.project.model.dto.request.auth;

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
    @Size(min = 6, max = 100, message = "The length must be between 6 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Special characters are not allowed")
    @NameExist(entityClass = User.class,existName = "username")
    private String username;
    @NotEmpty(message = "Full Name not empty")
    @Size(max = 100, message = "The maximum length is 100 characters")
    private String fullName;
    @NotEmpty(message = "Email not empty")
    @Email(message = "The email is invalid.")
    @NameExist(entityClass = User.class,existName = "email")
    private String email;
    @NotEmpty(message = "Password not empty")
    private String password;
    private Set<String> roles;
}
