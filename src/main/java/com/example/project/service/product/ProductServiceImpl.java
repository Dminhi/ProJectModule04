package com.example.project.service.product;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.ProductEditRequest;
import com.example.project.model.dto.request.ProductRequest;
import com.example.project.model.entity.Product;
import com.example.project.repository.ICategoryRepository;
import com.example.project.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    @Override
    public List<Product> findProductsByNameOrDescription(String findProduct) {
        return productRepository.findProductsByProductNameOrDescription(findProduct);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findNewProductAndStatusIsTrue(int top) {
        return productRepository.findNewProductAndStatusIsTrue(top);
    }

    @Override
    public List<Product> findAllByCategoryIdAndStatusIsTrue(Long categoryId) {
        return productRepository.findAllByCategoryIdAndStatusIsTrue(categoryId);
    }

    @Override
    public Product findByIdAndStatusIsTrue(Long id) throws NotFoundException {
        return productRepository.findByIdAndStatusIsTrue(id).orElseThrow(() -> new NotFoundException("product not found"));
    }

    @Override
    public Page<Product> findAllByStatusIsTrue(Pageable pageable) {
        return productRepository.findAllByStatusIsTrue(pageable);
    }

    @Override
    public Product findById(Long id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(()->new NotFoundException("product not found"));
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
    public void update(ProductEditRequest productEditRequest, Long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(()->new NotFoundException("product not found"));
        product.setProductName(productEditRequest.getProductName());
        product.setUpdatedAt(new Date());
        product.setCategory(categoryRepository.findById(productEditRequest.getCategory()).orElseThrow(()->new NotFoundException("product not found")));
        product.setDescription(product.getDescription());
        product.setStatus(productEditRequest.isStatus());
        product.setStockQuantity(productEditRequest.getStockQuantity());
        product.setUnitPrice(productEditRequest.getUnitPrice());
        productRepository.save(product);
    }

    @Override
    public void setDelete(Long id) {
        productRepository.setDeleteStatus(id);
    }
}
