package com.example.project.model.dto.response;

import com.example.project.model.entity.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String username;
    private String fullName;
    private String email;
    private String avatar;
    private String phone;
}
