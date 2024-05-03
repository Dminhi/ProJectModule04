package com.example.project.model.dto.request.auth;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FormLogin {
    @NotEmpty(message = "username not empty")
    private String username;
    @NotEmpty(message = "Password not empty")
    private String password;
}
