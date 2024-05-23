package com.example.project.service.category;

import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.category.CategoryEditRequest;
import com.example.project.model.dto.request.category.CategoryRequest;
import com.example.project.model.dto.response.CategoryResponse;
import com.example.project.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    Page<Category> findAll(Pageable pageable);
    Category findById(Long id) throws NotFoundException, DataNotFound;

    CategoryResponse save(CategoryRequest category);
    Category update(CategoryEditRequest categoryEditRequest, Long id) throws NotFoundException, RequestErrorException;
    Category changeCategoryStatus(Long id) throws NotFoundException, DataNotFound;

    List<CategoryResponse> findAllByCategoryTrue();
}
