package com.example.project.controller;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.ProductResponse;
import com.example.project.model.entity.Category;
import com.example.project.model.entity.Product;
import com.example.project.service.category.ICategoryService;
import com.example.project.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1")
public class HomeController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductResponse>> findByNameOrDescription(@RequestParam(required = false) String search) {
        List<ProductResponse> productResponses = productService.findProductsByNameOrDescription(search);
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }


    @GetMapping("/products")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5, sort = "productName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductResponse> productResponses = productService.findAllByStatusIsTrue(pageable);
        return new ResponseEntity<>(productResponses.getContent(), HttpStatus.OK);
    }

    @GetMapping("/products/new-products")
    public ResponseEntity<?> findNewProductAndStatusIsTrue(@RequestParam int top) {
        List<ProductResponse> productResponses = productService.findNewProductAndStatusIsTrue(top);
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @GetMapping("/products/categories/{categoryId}")
    public ResponseEntity<?> findProductByCategory(@PathVariable Long categoryId) {
        List<ProductResponse> productResponses = productService.findAllByCategoryIdAndStatusIsTrue(categoryId);
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) throws NotFoundException {
        ProductResponse productResponse = productService.findByIdAndStatusIsTrue(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> findCategoryByStatusTrue() {
        List<Category> categories = categoryService.findAllByCategoryTrue();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
