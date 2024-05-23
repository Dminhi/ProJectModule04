package com.example.project.model.dto.request.category;

import com.example.project.model.entity.Category;
import com.example.project.model.entity.Product;
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
public class CategoryEditRequest {
    @NotNull(message = "categoryName not null.")
    @Size(max = 100, message = "max length 100")
    private String categoryName;
    private String description;
}
