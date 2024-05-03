package com.example.project.model.dto.request.category;

import com.example.project.model.entity.Category;
import com.example.project.model.entity.Product;
import com.example.project.validator.NameExist;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Thuộc tính không được để trống.")
    @Size(max = 100, message = "Độ dài tối đa là 100 ký tự.")
//    @NameExist(entityClass = Category.class,existName = "categoryName")
    private String categoryName;
    private String description;
}
