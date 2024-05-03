package com.example.project.service.category;

import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.category.CategoryEditRequest;
import com.example.project.model.dto.request.category.CategoryRequest;
import com.example.project.model.entity.Category;
import com.example.project.repository.ICategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public Category update(CategoryEditRequest categoryEditRequest, Long id) throws NotFoundException, RequestErrorException {
        Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundException("category not found"));
        if(!Objects.equals(category.getCategoryName(), categoryEditRequest.getCategoryName())){
            if(categoryRepository.existsByCategoryName(categoryEditRequest.getCategoryName())){
                throw new RequestErrorException("category name exist");
            }
            else {
                category.setCategoryName(categoryEditRequest.getCategoryName());            }
        }
        category.setDescription(categoryEditRequest.getDescription());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void setDelete(Long id) throws NotFoundException {
        categoryRepository.setDeleteStatus(id);
    }

    @Override
    public List<Category> findAllByCategoryTrue() {
        return categoryRepository.findAllByStatusIsTrue();
    }
}
