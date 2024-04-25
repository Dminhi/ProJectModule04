package com.example.project.service.category;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.CategoryEditRequest;
import com.example.project.model.dto.request.CategoryRequest;
import com.example.project.model.entity.Category;
import com.example.project.model.entity.Product;
import com.example.project.repository.ICategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceIplm implements ICategoryService{
    @Autowired
    ICategoryRepository categoryRepository;
    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category findById(Long id) throws NotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("category not found"));
    }

    @Override
    public Category save(CategoryRequest category) {
        return categoryRepository.save(Category.builder()
                        .categoryName(category.getCategoryName())
                        .description(category.getDescription())
                        .status(true)
                .build());
    }

    @Override
    @Transactional
    public void update(CategoryEditRequest categoryEditRequest, Long id) throws NotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundException("category not found"));
        category.setCategoryName(categoryEditRequest.getCategoryName());
        category.setDescription(categoryEditRequest.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public void setDelete(Long id) throws NotFoundException {
        categoryRepository.setDeleteStatus(id);
    }
}
