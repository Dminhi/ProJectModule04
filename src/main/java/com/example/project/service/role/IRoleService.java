package com.example.project.service.role;

import com.example.project.model.entity.Product;
import com.example.project.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoleService {
    List<Role> findAll();
}
