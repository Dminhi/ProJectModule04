package com.example.project.service.product;

import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.product.ProductEditRequest;
import com.example.project.model.dto.request.product.ProductRequest;
import com.example.project.model.dto.response.ProductResponse;
import com.example.project.model.dto.response.WishListResponse;
import com.example.project.model.entity.Product;
import com.example.project.model.entity.WishList;
import com.example.project.repository.ICategoryRepository;
import com.example.project.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> findProductsByNameOrDescription(String findProduct) {
        List<Product> products = productRepository.findProductsByProductNameOrDescription(findProduct);
        return products.stream().map(product-> ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .productName(product.getProductName())
                .image(product.getImage())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build()).toList();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<ProductResponse> findNewProductAndStatusIsTrue(int top) {
        List<Product> products = productRepository.findNewProductAndStatusIsTrue(top);
        return products.stream().map(product-> ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .productName(product.getProductName())
                .image(product.getImage())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build()).toList();
    }

    @Override
    public List<ProductResponse> findAllByCategoryIdAndStatusIsTrue(Long categoryId) {
        List<Product> products = productRepository.findAllByCategoryIdAndStatusIsTrue(categoryId);
        return products.stream().map(product-> ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .productName(product.getProductName())
                .image(product.getImage())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build()).toList();
    }

    @Override
    public ProductResponse findByIdAndStatusIsTrue(Long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("product not found"));
        return ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .productName(product.getProductName())
                .image(product.getImage())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build();    }

    @Override
    public Page<ProductResponse> findAllByStatusIsTrue(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByStatusIsTrue(pageable);
        List<ProductResponse> productResponseList = productPage.stream().map(product-> ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .productName(product.getProductName())
                .image(product.getImage())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build()).toList();
        return new PageImpl<>(productResponseList, pageable, productPage.getTotalElements());
    }

    @Override
    public ProductResponse findById(Long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("product not found"));
        return ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .productName(product.getProductName())
                .image(product.getImage())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }

    @Override
    public Product save(ProductRequest productRequest) throws NotFoundException {
        Product product = Product.builder()
                .status(true)
                .productName(productRequest.getProductName())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .createdAt(new Date())
                .description(productRequest.getDescription())
                .sku(UUID.randomUUID().toString())
                .category(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()-> new NotFoundException("category not found")))
                .build();
        return productRepository.save(product);
    }

    @Override
    public ProductResponse update(ProductEditRequest productEditRequest, Long id) throws NotFoundException, RequestErrorException {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("product not found"));
        if (!Objects.equals(product.getProductName(), productEditRequest.getProductName())) {
            if (productRepository.existsByProductName(productEditRequest.getProductName())) {
                throw new RequestErrorException("product name exist");
            } else {
                product.setProductName(productEditRequest.getProductName());
            }
        }
            product.setUpdatedAt(new Date());
            product.setCategory(categoryRepository.findById(productEditRequest.getCategory()).orElseThrow(() -> new NotFoundException("product not found")));
            product.setDescription(product.getDescription());
            product.setStatus(productEditRequest.isStatus());
            product.setStockQuantity(productEditRequest.getStockQuantity());
            product.setUnitPrice(productEditRequest.getUnitPrice());
            productRepository.save(product);
            return ProductResponse.builder()
                    .unitPrice(product.getUnitPrice())
                    .productName(product.getProductName())
                    .image(product.getImage())
                    .stockQuantity(product.getStockQuantity())
                    .categoryName(product.getCategory().getCategoryName())
                    .build();
    }

    @Override
    public void setDelete(Long id) throws NotFoundException {
        productRepository.setDeleteStatus(id);
    }
}

