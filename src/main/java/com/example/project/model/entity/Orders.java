package com.example.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;
    private BigDecimal totalPrice;
    private OderStatusName oderStatusName;
    private String note;
    private String receiveName;
    private String receiveAddress;
    @Column(length = 15, unique = true, nullable = false)
    private String receivePhone;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private Date createdAt;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date receiveAt;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList;
}
