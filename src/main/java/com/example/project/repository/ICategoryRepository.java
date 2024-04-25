package com.example.project.repository;

import com.example.project.model.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {
    @Modifying
    @Transactional
    @Query(value = "update Category set status = false where id = :id",nativeQuery = true)
    void setDeleteStatus(@Param("id") Long id);
}
