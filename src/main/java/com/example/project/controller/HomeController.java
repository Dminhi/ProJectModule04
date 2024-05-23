package com.example.project.controller;

import com.example.project.config.ConvertPageToPaginationDTO;
import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.CategoryResponse;
import com.example.project.model.dto.response.ProductResponse;
import com.example.project.model.dto.responsewapper.EHttpStatus;
import com.example.project.model.dto.responsewapper.ResponseWapper;
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
    public ResponseEntity<?> findByNameOrDescription(@RequestParam(required = false) String search) throws DataNotFound {
        List<ProductResponse> productResponses = productService.findProductsByNameOrDescription(search);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                productResponses ), HttpStatus.OK);
    }


    @GetMapping("/products")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5, sort = "productName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductResponse> productResponses = productService.findAllByStatusIsTrue(pageable);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ConvertPageToPaginationDTO.convertPageToPaginationDTO(productResponses)), HttpStatus.OK);
    }
    @GetMapping("/products/new-products")
    public ResponseEntity<?> findNewProductAndStatusIsTrue(@RequestParam int top) {
        List<ProductResponse> productResponses = productService.findNewProductAndStatusIsTrue(top);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                productResponses ), HttpStatus.OK);    }

    @GetMapping("/products/categories/{categoryId}")
    public ResponseEntity<?> findProductByCategory(@PathVariable Long categoryId,@PageableDefault(page = 0, size = 5, sort = "productName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductResponse> productResponses = productService.findAllByCategoryIdAndStatusIsTrue(categoryId,pageable);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ConvertPageToPaginationDTO.convertPageToPaginationDTO(productResponses)), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) throws NotFoundException, DataNotFound {
        ProductResponse productResponse = productService.findByIdAndStatusIsTrue(id);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                productResponse), HttpStatus.OK);    }

    @GetMapping("/categories")
    public ResponseEntity<?> findCategoryByStatusTrue() {
        List<CategoryResponse> categories = categoryService.findAllByCategoryTrue();
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                categories), HttpStatus.OK);
    }
}
