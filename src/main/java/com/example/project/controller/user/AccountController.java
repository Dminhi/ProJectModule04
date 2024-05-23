package com.example.project.controller.user;

import com.example.project.config.ConvertPageToPaginationDTO;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.account.AccountEditPassword;
import com.example.project.model.dto.request.account.AccountEditRequest;
import com.example.project.model.dto.response.UserResponse;
import com.example.project.model.dto.responsewapper.EHttpStatus;
import com.example.project.model.dto.responsewapper.ResponseWapper;
import com.example.project.model.entity.User;
import com.example.project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/user")
public class AccountController {
    @Autowired
    private IUserService userService;
    @GetMapping("/account")
    public ResponseEntity<?> userInfor() throws NotFoundException {
        UserResponse user = userService.getUserInfor();
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                user), HttpStatus.OK);
    }

    @PutMapping("/account")
    public ResponseEntity<?> updateAccount(@RequestBody AccountEditRequest accountEditRequest) throws NotFoundException, RequestErrorException {
        UserResponse user = userService.updateAccount(accountEditRequest);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                user), HttpStatus.OK);
    }

    @PutMapping("/account/change-password")
    public ResponseEntity<?> updatePasswordAccount(@RequestBody AccountEditPassword accountEditPassword) throws NotFoundException, RequestErrorException {
       UserResponse user = userService.changePassword(accountEditPassword);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                user), HttpStatus.OK);
    }
}
