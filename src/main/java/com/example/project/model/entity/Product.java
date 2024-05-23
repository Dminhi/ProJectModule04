package com.example.project.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(length = 100, unique = true, nullable = false)
    private String sku;
    @Column(length = 100, unique = true, nullable = false)
    @NotEmpty(message = "Thuộc tính không được để trống.")
    @Size(max = 100, message = "Độ dài tối đa là 100 ký tự.")
    private String productName;
    private boolean status;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Double unitPrice;
    @Column(nullable = false)
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
}
