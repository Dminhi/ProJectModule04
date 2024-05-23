package com.example.project.service.category;

import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.category.CategoryEditRequest;
import com.example.project.model.dto.request.category.CategoryRequest;
import com.example.project.model.dto.response.CategoryResponse;
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
    public Category findById(Long id) throws NotFoundException, DataNotFound {
        return categoryRepository.findById(id).orElseThrow(() -> new DataNotFound("category not found"));
    }

    @Override
    public CategoryResponse save(CategoryRequest category) {
        Category categories = new Category();
        categories.setCategoryName(category.getCategoryName());
        categories.setDescription(category.getDescription());
        categories.setStatus(true);
        Category newCategory = categoryRepository.save(categories);
        return toCategoryResponse(newCategory);
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
    public Category changeCategoryStatus(Long id) throws NotFoundException, DataNotFound {

        categoryRepository.changeCategoryStatus(id);
        return categoryRepository.findById(id).orElseThrow(()->new DataNotFound("category not found"));
    }

    @Override
    public List<CategoryResponse> findAllByCategoryTrue() {
        return   categoryRepository.findAllByStatusIsTrue().stream().map(CategoryServiceIplm::toCategoryResponse).toList();

    }

    public static CategoryResponse toCategoryResponse(Category category) {
        if (category == null) {
            return null; // Nếu category là null, trả về null để tránh lỗi
        }

        // Sử dụng Builder của CategoryResponse để tạo một đối tượng mới
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .build();
    }
}

