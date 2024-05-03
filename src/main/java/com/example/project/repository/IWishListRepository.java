package com.example.project.repository;

import com.example.project.model.entity.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWishListRepository extends JpaRepository<WishList,Long> {
    boolean existsByProductIdAndUserId(Long productId,Long userId);

    @Transactional
    void deleteWishListByProductIdAndUserId(Long productId,Long userId);

    @Transactional
    void deleteWishListById(Long id);

    List<WishList> findAllByUserId(Long id);
}
