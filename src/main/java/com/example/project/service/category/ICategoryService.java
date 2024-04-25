package com.example.project.service.category;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.CategoryEditRequest;
import com.example.project.model.dto.request.CategoryRequest;
import com.example.project.model.dto.request.ProductEditRequest;
import com.example.project.model.entity.Category;
import com.example.project.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryService {
    Page<Category> findAll(Pageable pageable);
    Category findById(Long id) throws NotFoundException;

    Category save(CategoryRequest category);
    void update(CategoryEditRequest categoryEditRequest, Long id) throws NotFoundException;
    void setDelete(Long id) throws NotFoundException;
}
