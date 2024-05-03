package com.example.project.service.product;

import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.product.ProductEditRequest;
import com.example.project.model.dto.request.product.ProductRequest;
import com.example.project.model.dto.response.ProductResponse;
import com.example.project.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    List<ProductResponse> findProductsByNameOrDescription(String findProduct);
    Page<Product> findAll(Pageable pageable);

    List<ProductResponse> findNewProductAndStatusIsTrue(int top);


    ProductResponse findByIdAndStatusIsTrue(Long id) throws NotFoundException;

    Page<ProductResponse> findAllByStatusIsTrue(Pageable pageable);

    ProductResponse findById(Long id) throws NotFoundException;

    Product save(ProductRequest productRequest) throws NotFoundException;

    List<ProductResponse> findAllByCategoryIdAndStatusIsTrue(Long id);

    ProductResponse update(ProductEditRequest productEditRequest, Long id) throws NotFoundException, RequestErrorException;

    void setDelete(Long id) throws NotFoundException;

}
