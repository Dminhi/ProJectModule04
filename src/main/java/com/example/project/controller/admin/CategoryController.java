package com.example.project.controller.admin;

import com.example.project.config.ConvertPageToPaginationDTO;
import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.category.CategoryEditRequest;
import com.example.project.model.dto.request.category.CategoryRequest;
import com.example.project.model.dto.response.CategoryResponse;
import com.example.project.model.dto.responsewapper.EHttpStatus;
import com.example.project.model.dto.responsewapper.ResponseWapper;
import com.example.project.model.entity.Category;
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
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5, sort = "categoryName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> categories = categoryService.findAll(pageable);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ConvertPageToPaginationDTO.convertPageToPaginationDTO(categories)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long id) throws NotFoundException, DataNotFound {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                category), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequest category){
        CategoryResponse c = categoryService.save(category);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                c), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody CategoryEditRequest categoryEditRequest) throws NotFoundException, RequestErrorException {
        Category category = categoryService.update(categoryEditRequest, id);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                category), HttpStatus.OK);    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> changeCategoryStatus(@PathVariable Long id) throws NotFoundException, DataNotFound {
        Category category = categoryService.changeCategoryStatus(id);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                category), HttpStatus.OK);}
}
