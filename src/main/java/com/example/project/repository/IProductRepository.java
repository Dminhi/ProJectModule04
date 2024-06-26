package com.example.project.repository;

import com.example.project.model.dto.response.ProductResponse;
import com.example.project.model.entity.Product;
import com.example.project.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p  " +
            "                  WHERE p.status=true and p.productName LIKE %:search%  " +
            "                  OR p.description LIKE %:search%")
    List<Product> findProductsByProductNameOrDescription(@Param("search") String search);

    @Query(value = "select * from Product p where p.status = true order by createdAt desc limit :top",nativeQuery = true)
    List<Product> findNewProductAndStatusIsTrue(@Param("top") int top );

    List<Product> findAllByCategoryIdAndStatusIsTrue(Long categoryId,Pageable pageable);

    Page<Product> findAllByStatusIsTrue(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update Product p set status = !p.status where productId = :id",nativeQuery = true)
    void changeProductStatus(@Param("id") Long id);

    boolean existsByProductName(String name);
}
