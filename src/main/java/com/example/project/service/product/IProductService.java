package com.example.project.service.product;

import com.example.project.exception.DataNotFound;
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
    List<ProductResponse> findProductsByNameOrDescription(String findProduct) throws DataNotFound;
    Page<Product> findAll(Pageable pageable);

    List<ProductResponse> findNewProductAndStatusIsTrue(int top);


    ProductResponse findByIdAndStatusIsTrue(Long id) throws NotFoundException, DataNotFound;

    Page<ProductResponse> findAllByStatusIsTrue(Pageable pageable);

    Product findById(Long id) throws NotFoundException, DataNotFound;

    Product save(ProductRequest productRequest) throws NotFoundException;

    Page<ProductResponse> findAllByCategoryIdAndStatusIsTrue(Long id, Pageable pageable);

    Product update(ProductEditRequest productEditRequest, Long id) throws NotFoundException, RequestErrorException;

    Product changeProductStatus(Long id) throws NotFoundException, DataNotFound;

    List<ProductResponse> findAllProductByShoppingCartUser() throws NotFoundException, DataNotFound;



}
