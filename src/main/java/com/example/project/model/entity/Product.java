package com.example.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, unique = true, nullable = false)
    private String sku;
    @Column(length = 100, unique = true, nullable = false)
    @NotEmpty(message = "Thuộc tính không được để trống.")
    @Size(max = 100, message = "Độ dài tối đa là 100 ký tự.")
    private String productName;
    private boolean status;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(precision = 10, scale = 2, nullable = false) // Định dạng cột DECIMAL(10,2) và không cho phép giá trị null
    private BigDecimal unitPrice;
    @Column(nullable = false) // Không cho phép giá trị null
    @Min(value = 0, message = "Giá trị phải lớn hơn hoặc bằng 0.")
    private Integer stockQuantity;
    private String image;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ShoppingCart> shoppingCarts;
}