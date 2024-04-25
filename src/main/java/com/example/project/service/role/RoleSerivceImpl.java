package com.example.project.service.role;

import com.example.project.model.entity.Role;
import com.example.project.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleSerivceImpl implements IRoleService {
    @Autowired
    IRoleRepository repository;

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }
}
