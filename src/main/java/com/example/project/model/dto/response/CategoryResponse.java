package com.example.project.model.dto.response;

import com.example.project.model.entity.Category;
import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private String description;
}
