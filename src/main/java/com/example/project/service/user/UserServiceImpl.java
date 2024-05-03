package com.example.project.service.user;

import com.example.project.exception.AccountLockedException;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.account.AccountEditPassword;
import com.example.project.model.dto.request.account.AccountEditRequest;
import com.example.project.model.dto.request.auth.FormLogin;
import com.example.project.model.dto.request.auth.FormRegister;
import com.example.project.model.dto.response.JWTResponse;
import com.example.project.model.entity.Role;
import com.example.project.model.entity.RoleName;
import com.example.project.model.entity.User;
import com.example.project.repository.IRoleRepository;
import com.example.project.repository.IUserRepository;
import com.example.project.securiry.jwt.JWTProvider;
import com.example.project.securiry.principle.UserDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IRoleRepository iRoleRepository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean register(FormRegister formRegister) {
        User user = User.builder()
                .createdAt(new Date())
                .email(formRegister.getEmail())
                .fullName(formRegister.getFullName())
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .status(true)
                .build();
        if (formRegister.getRoles() != null && !formRegister.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            formRegister.getRoles().forEach(
                    r -> {
                        switch (r) {
                            case "ADMIN":
                                roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_ADMIN).orElseThrow(() -> new NoSuchElementException("role not found")));
                            case "MANAGER":
                                roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_MANAGER).orElseThrow(() -> new NoSuchElementException("role not found")));
                            case "USER":
                                roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("role not found")));
                            default:
                                roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("role not found")));
                        }
                    }
            );
            user.setRoleSet(roles);
        } else {
            // mac dinh la user
            Set<Role> roles = new HashSet<>();
            roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("role not found")));
            user.setRoleSet(roles);
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public JWTResponse login(FormLogin formLogin) throws AccountLockedException, NotFoundException {
        // xac thực username vaf password
        Authentication authentication = null;
        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        } catch (AuthenticationException e) {
            throw new NotFoundException("username or password incorrect");
        }
        UserDetailsCustom detailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        if (!detailsCustom.isStatus()) {
            throw new AccountLockedException("account is locked");
        }
        String accessToken = jwtProvider.generateAccessToken(detailsCustom);
        return JWTResponse.builder()
                .email(detailsCustom.getEmail())
                .fullName(detailsCustom.getFullName())
                .roleSet(detailsCustom.getAuthorities())
                .status(detailsCustom.isStatus())
                .accessToken(accessToken)
                .build();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User changeStatus(Long id) throws NotFoundException, RequestErrorException {
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("user not found"));
        if(user.getRoleSet().stream().anyMatch(role -> role.getRoleName().name().equals("ROLE_ADMIN"))){
            throw new RequestErrorException("Can't change admin status");
        }
        user.setStatus(!user.isStatus());
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findUserByUsername(String search) {
        return userRepository.findAllByUsernameContains(search);
    }

    @Override
    public User getUserInfor() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        return userRepository.findById(userDetailsCustom.getId()).orElseThrow(() -> new NotFoundException("user not found"));
    }

    @Override
    public User updateAccount(AccountEditRequest accountEditRequest) throws NotFoundException, RequestErrorException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        User user = userRepository.findById(userDetailsCustom.getId()).orElseThrow(() -> new NotFoundException("user not found"));
        user.setEmail(accountEditRequest.getEmail());
        user.setFullName(accountEditRequest.getFullName());
        if(!Objects.equals(user.getPhone(), accountEditRequest.getPhone())){
            if(userRepository.existsByPhone(accountEditRequest.getPhone())){
                throw new RequestErrorException("phone number exist");
            }
            else {
                user.setPhone(accountEditRequest.getPhone());}
        }
        user.setPhone(accountEditRequest.getPhone());
        user.setUpdatedAt(new Date());
        user.setAvatar(accountEditRequest.getAvatar());
        userRepository.save(user);
        return user;
    }

    @Override
    public User changePassword(AccountEditPassword accountEditPassword) throws NotFoundException, RequestErrorException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        User user = userRepository.findById(userDetailsCustom.getId()).orElseThrow(() -> new NotFoundException("user not found"));
        if (!passwordEncoder.matches(accountEditPassword.getOldPassword(),user.getPassword())) {
            throw new RequestErrorException("password incorrect");
        }
        if(!accountEditPassword.getNewPassWord().equals(accountEditPassword.getConfirmPassWord())){
            throw new RequestErrorException("confirm password incorrect");
        }
        user.setPassword(passwordEncoder.encode(accountEditPassword.getNewPassWord()));
        userRepository.save(user);
        return user;
    }
}
