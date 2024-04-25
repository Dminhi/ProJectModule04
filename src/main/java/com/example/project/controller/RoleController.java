package com.example.project.controller;

import com.example.project.model.entity.Product;
import com.example.project.model.entity.Role;
import com.example.project.service.product.IProductService;
import com.example.project.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/roles")
public class RoleController {
    @Autowired
    IRoleService roleService;
    @GetMapping()
    public ResponseEntity<List<Role>> findAll() {
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
