package com.example.project.controller.admin;

import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.entity.User;
import com.example.project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/users")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping()
    public ResponseEntity<Page<User>> findAll(@PageableDefault(page = 0, size = 5, sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> changeStatus(@PathVariable Long id) throws NotFoundException, RequestErrorException {
        User user = userService.changeStatus(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> findUserByUsername(@RequestParam(required = false) String search) {
        List<User> users = userService.findUserByUsername(search);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
