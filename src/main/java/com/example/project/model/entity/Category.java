package com.example.project.model.entity;

import com.example.project.validator.NameExist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.w3c.dom.Text;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false) // Định dạng cột VARCHAR(100), không cho phép giá trị null
    private String categoryName;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status;
}
