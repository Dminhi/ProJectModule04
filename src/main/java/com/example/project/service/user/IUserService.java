package com.example.project.service.user;

import com.example.project.exception.AccountLockedException;
import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.account.AccountEditPassword;
import com.example.project.model.dto.request.account.AccountEditRequest;
import com.example.project.model.dto.request.auth.FormLogin;
import com.example.project.model.dto.request.auth.FormRegister;
import com.example.project.model.dto.response.JWTResponse;
import com.example.project.model.dto.response.UserResponse;
import com.example.project.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    boolean register(FormRegister formRegister);
    JWTResponse login(FormLogin formLogin) throws AccountLockedException, NotFoundException;
    Page<User> findAll(Pageable pageable) throws NotFoundException;

    User changeStatus(Long id) throws NotFoundException, RequestErrorException;

    List<UserResponse> findUserByUsername(String search) throws DataNotFound;

    UserResponse getUserInfor() throws NotFoundException;

    UserResponse updateAccount(AccountEditRequest accountEditRequest) throws NotFoundException, RequestErrorException;

    UserResponse changePassword(AccountEditPassword accountEditPassword) throws NotFoundException, RequestErrorException;
}
