package com.example.project.repository;

import com.example.project.model.entity.ShoppingCart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface   IShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    Optional<ShoppingCart> findByProductProductIdAndUserId(Long productId,Long userId);

    @Transactional
    void deleteShoppingCartByUserId(Long id);

    List<ShoppingCart> findAllByUserId(Long id);

}
