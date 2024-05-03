package com.example.project.controller.admin;

import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.product.ProductEditRequest;
import com.example.project.model.dto.request.product.ProductRequest;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.dto.response.ProductResponse;
import com.example.project.model.entity.Product;
import com.example.project.service.order.IOrderService;
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

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class AdminController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/history")
    public ResponseEntity<?> findAllOrders() throws NotFoundException {
        List<OrderResponse> orderResponseList = orderService.getAllOrder();
        return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findAll(@PageableDefault(page = 0, size = 5, sort = "productName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) throws NotFoundException {
        ProductResponse productResponse = productService.findById(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest productRequest) throws NotFoundException {
        Product p = productService.save(productRequest);
        return new ResponseEntity<>(p,HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id,@RequestBody ProductEditRequest productEditRequest) throws NotFoundException, RequestErrorException {
       ProductResponse productResponse = productService.update(productEditRequest, id);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> changeProductStatus(@PathVariable Long id) throws NotFoundException {
        productService.setDelete(id);
        return new ResponseEntity<>("Delete Successful",HttpStatus.OK);
    }
}
