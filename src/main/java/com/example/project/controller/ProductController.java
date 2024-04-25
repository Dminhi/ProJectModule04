package com.example.project.controller;

import com.example.project.exception.NotFoundException;
import com.example.project.model.entity.Product;
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
@RequestMapping("/api.myservice.com/v1/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<Product>> findByNameOrDescription(@RequestParam(required = false) String search) {
        List<Product> products = productService.findProductsByNameOrDescription(search);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<Page<Product>> findAll(@PageableDefault(page = 0, size = 5, sort = "productName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> products = productService.findAllByStatusIsTrue(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/new-products")
    public ResponseEntity<List<Product>> findNewProductAndStatusIsTrue(@RequestParam int top) {
        List<Product> products = productService.findNewProductAndStatusIsTrue(top);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<Product>> findProductByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.findAllByCategoryIdAndStatusIsTrue(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) throws NotFoundException {
        Product product = productService.findByIdAndStatusIsTrue(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
