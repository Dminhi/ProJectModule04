package com.example.project.controller.admin;

import com.example.project.config.ConvertPageToPaginationDTO;
import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.response.UserResponse;
import com.example.project.model.dto.responsewapper.EHttpStatus;
import com.example.project.model.dto.responsewapper.ResponseWapper;
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
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5, sort = "username", direction = Sort.Direction.ASC) Pageable pageable) throws NotFoundException {
        Page<User> users = userService.findAll(pageable);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ConvertPageToPaginationDTO.convertPageToPaginationDTO(users)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id) throws NotFoundException, RequestErrorException {
        User user = userService.changeStatus(id);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                user), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findUserByUsername(@RequestParam(required = false) String search) throws DataNotFound {
        List<UserResponse> users = userService.findUserByUsername(search);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                users), HttpStatus.OK);    }

}
