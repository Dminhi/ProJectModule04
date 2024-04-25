package com.example.project.repository;

import com.example.project.model.entity.Product;
import com.example.project.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    @Modifying
    @Transactional
    @Query(value = "update User set status = not status where id = :id",nativeQuery = true)
    void changeStatus(@Param("id") Long id);
    List<User> findAllByUsernameContains(String search);

}
