package com.example.project.controller;
import com.example.project.config.ConvertPageToPaginationDTO;
import com.example.project.exception.AccountLockedException;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.auth.FormLogin;
import com.example.project.model.dto.request.auth.FormRegister;
import com.example.project.model.dto.response.JWTResponse;
import com.example.project.model.dto.responsewapper.EHttpStatus;
import com.example.project.model.dto.responsewapper.ResponseWapper;
import com.example.project.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1/auth")
public class AuthController {
    @Autowired
    private IUserService userService;
    @PostMapping("/sign-in")
    public ResponseEntity<?> doLogin(@Valid @RequestBody FormLogin formLogin) throws AccountLockedException, NotFoundException {
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                userService.login(formLogin)), HttpStatus.OK);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> doRegister(@Valid @RequestBody FormRegister formRegister){
        boolean check = userService.register(formRegister);
        if (check){
            Map<String,String> map = new HashMap<>();
            map.put("message","Account create successfully");
            return new ResponseEntity<>(map,HttpStatus.CREATED);
        }else {
            throw new RuntimeException("something is error");
        }
    }
}
