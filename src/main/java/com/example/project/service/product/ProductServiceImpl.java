package com.example.project.service.product;

import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.product.ProductEditRequest;
import com.example.project.model.dto.request.product.ProductRequest;
import com.example.project.model.dto.response.ProductResponse;
import com.example.project.model.dto.response.WishListResponse;
import com.example.project.model.entity.Product;
import com.example.project.model.entity.ShoppingCart;
import com.example.project.model.entity.User;
import com.example.project.model.entity.WishList;
import com.example.project.repository.ICategoryRepository;
import com.example.project.repository.IProductRepository;
import com.example.project.repository.IShoppingCartRepository;
import com.example.project.repository.IUserRepository;
import com.example.project.securiry.principle.UserDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IShoppingCartRepository shoppingCartRepository;

    @Override
    public List<ProductResponse> findProductsByNameOrDescription(String findProduct) throws DataNotFound {
        List<Product> products = productRepository.findProductsByProductNameOrDescription(findProduct);
        if(products.isEmpty()){throw new DataNotFound("product not found");}
        return products.stream().map(product-> ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .id(product.getProductId())
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
                .id(product.getProductId())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build()).toList();
    }

    @Override
    public Page<ProductResponse> findAllByCategoryIdAndStatusIsTrue(Long categoryId,Pageable pageable) {
        List<Product> products = productRepository.findAllByCategoryIdAndStatusIsTrue(categoryId,pageable);
        List<ProductResponse> productResponseList = products.stream().map(product-> ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .productName(product.getProductName())
                .image(product.getImage())
                .id(product.getProductId())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build()).toList();
        return new PageImpl<>(productResponseList, pageable, (long)productResponseList.size());
    }

    @Override
    public ProductResponse findByIdAndStatusIsTrue(Long id) throws  DataNotFound {
        Product product = productRepository.findById(id).orElseThrow(()-> new DataNotFound("product not found"));
        return ProductResponse.builder()
                .unitPrice(product.getUnitPrice())
                .productName(product.getProductName())
                .image(product.getImage())
                .id(product.getProductId())
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
                .id(product.getProductId())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getCategoryName())
                .build()).toList();
        return new PageImpl<>(productResponseList, pageable, productPage.getTotalElements());
    }

    @Override
    public Product findById(Long id) throws  DataNotFound {
        return productRepository.findById(id).orElseThrow(()-> new DataNotFound("product not found"));
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
    public Product update(ProductEditRequest productEditRequest, Long id) throws NotFoundException, RequestErrorException {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("product not found"));
        if (!Objects.equals(product.getProductName(), productEditRequest.getProductName())) {
            if (productRepository.existsByProductName(productEditRequest.getProductName())) {
                throw new RequestErrorException("product name exist");
            } else {
                product.setProductName(productEditRequest.getProductName());
            }
        }
            product.setUpdatedAt(new Date());
            product.setCategory(categoryRepository.findById(productEditRequest.getCategory()).orElseThrow(() -> new NotFoundException("category not found")));
            product.setDescription(product.getDescription());
            product.setStatus(productEditRequest.isStatus());
            product.setStockQuantity(productEditRequest.getStockQuantity());
            product.setUnitPrice(productEditRequest.getUnitPrice());
            productRepository.save(product);
            return product;
    }

    @Override
    public Product changeProductStatus(Long id) throws NotFoundException, DataNotFound {
      productRepository.changeProductStatus(id);
        return productRepository.findById(id).orElseThrow(()->new DataNotFound("product not found"));
    }

    @Override
    public List<ProductResponse> findAllProductByShoppingCartUser() throws NotFoundException, DataNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        List<Product> products = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCartRepository.findAllByUserId(userDetailsCustom.getId()))
        {
            products.add(shoppingCart.getProduct());
        }
        if(products.isEmpty()){throw new DataNotFound("product not found");}
        return  products.stream().map(ProductServiceImpl::toProductResponse).toList();
    }

    public static ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null; // Nếu product là null, trả về null để tránh lỗi
        }

        // Sử dụng Builder của ProductResponse để tạo một đối tượng mới
        return ProductResponse.builder()
                .id(product.getProductId())
                .productName(product.getProductName())
                .stockQuantity(product.getStockQuantity())
                .unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .categoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null)
                .build();
    }
}

