package com.example.project.model.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullAddress;
    private String phone;
    private String receiveName;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
