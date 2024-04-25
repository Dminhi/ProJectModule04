package com.example.project.service.product;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.ProductEditRequest;
import com.example.project.model.dto.request.ProductRequest;
import com.example.project.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findProductsByNameOrDescription(String findProduct);
    Page<Product> findAll(Pageable pageable);

    List<Product> findNewProductAndStatusIsTrue(int top);


    Product findByIdAndStatusIsTrue(Long id) throws NotFoundException;

    Page<Product> findAllByStatusIsTrue(Pageable pageable);

    Product findById(Long id) throws NotFoundException;

    Product save(ProductRequest productRequest) throws NotFoundException;

    List<Product> findAllByCategoryIdAndStatusIsTrue(Long id);

    void update(ProductEditRequest productEditRequest, Long id) throws NotFoundException;

    void setDelete(Long id) throws NotFoundException;

}
