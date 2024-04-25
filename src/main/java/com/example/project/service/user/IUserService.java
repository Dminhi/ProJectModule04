package com.example.project.service.user;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.FormLogin;
import com.example.project.model.dto.request.FormRegister;
import com.example.project.model.dto.response.JWTResponse;
import com.example.project.model.entity.Product;
import com.example.project.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    boolean register(FormRegister formRegister);
    JWTResponse login(FormLogin formLogin);
    Page<User> findAll(Pageable pageable);

    boolean changeStatus(Long id);

    List<User> findUserByUsername(String search);
}
