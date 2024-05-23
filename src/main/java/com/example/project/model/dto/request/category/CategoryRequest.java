package com.example.project.model.dto.request.category;

import com.example.project.model.entity.Category;
import com.example.project.validator.NameExist;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryRequest {
    @NotNull(message = "categoryName not null.")
    @Size(max = 100, message = "max length 100")
    @NameExist(entityClass = Category.class,existName = "categoryName",message = "categoryName already exist")
    private String categoryName;
    private String description;
}
