package com.example.project.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class OrderDetail {
    @EmbeddedId
    private CompositeKey id;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders orders;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    private String name;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false) // Không cho phép giá trị null
    @Min(value = 0, message = "Giá trị phải lớn hơn hoặc bằng 0.")
    private int orderQuantity;
}
