package com.example.project.controller;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.ProductEditRequest;
import com.example.project.model.dto.request.ProductRequest;
import com.example.project.model.entity.Product;
import com.example.project.service.product.IProductService;
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
@RequestMapping("/api.myservice.com/v1/admin")
public class AdminController {
    @Autowired
    IProductService productService;
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findAll(@PageableDefault(page = 0, size = 5, sort = "productName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) throws NotFoundException {
        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest productRequest) throws NotFoundException {
        Product p = productService.save(productRequest);
        return new ResponseEntity<>(p,HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> update(@Valid @PathVariable Long id,@RequestBody ProductEditRequest productEditRequest) throws NotFoundException {
        productService.update(productEditRequest, id);
        return new ResponseEntity<>("Update Successful",HttpStatus.OK);
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> changeProductStatus(@PathVariable Long id) throws NotFoundException {
        productService.setDelete(id);
        return new ResponseEntity<>("Delete Successful",HttpStatus.OK);
    }
}
