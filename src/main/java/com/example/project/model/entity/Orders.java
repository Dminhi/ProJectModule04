package com.example.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String serialNumber;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus oderStatus;
    private String note;
    private String receiveName;
    private String receiveAddress;
    @Column(length = 15, nullable = false)
    private String receivePhone;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private Date createdAt;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date receiveAt;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"password"})
    private User user;
}
