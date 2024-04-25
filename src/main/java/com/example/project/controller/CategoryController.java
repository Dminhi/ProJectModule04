package com.example.project.controller;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.CategoryEditRequest;
import com.example.project.model.dto.request.CategoryRequest;
import com.example.project.model.dto.request.ProductEditRequest;
import com.example.project.model.dto.request.ProductRequest;
import com.example.project.model.entity.Category;
import com.example.project.model.entity.Product;
import com.example.project.service.category.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/categories")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;
    @GetMapping()
    public ResponseEntity<Page<Category>> findAll(@PageableDefault(page = 0, size = 5, sort = "categoryName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> categories = categoryService.findAll(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) throws NotFoundException {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequest category){
        Category c = categoryService.save(category);
        return new ResponseEntity<>(c,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,@RequestBody CategoryEditRequest categoryEditRequest) throws NotFoundException {
        categoryService.update(categoryEditRequest, id);
        return new ResponseEntity<>("Update Successful",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> changeCategoryStatus(@PathVariable Long id) throws NotFoundException {
        categoryService.setDelete(id);
        return new ResponseEntity<>("Delete Successful",HttpStatus.OK);
    }
}
