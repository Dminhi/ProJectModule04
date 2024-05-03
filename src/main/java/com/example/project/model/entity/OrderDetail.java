package com.example.project.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class OrderDetail {
    @EmbeddedId
    private CompositeKey id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String name;
    @Column(nullable = false)
    private Double unitPrice;
    @Column(nullable = false) // Không cho phép giá trị null
    @Min(value = 0, message = "Giá trị phải lớn hơn hoặc bằng 0.")
    private int orderQuantity;
}
