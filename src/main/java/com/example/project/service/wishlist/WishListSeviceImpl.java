package com.example.project.service.wishlist;

import com.example.project.exception.AccountLockedException;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.RequestErrorException;
import com.example.project.model.dto.request.wishlist.WishListRequest;
import com.example.project.model.dto.response.WishListResponse;
import com.example.project.model.entity.WishList;
import com.example.project.repository.IProductRepository;
import com.example.project.repository.IUserRepository;
import com.example.project.repository.IWishListRepository;
import com.example.project.securiry.principle.UserDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListSeviceImpl implements IWishListService{
    @Autowired
    private IWishListRepository wishListRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public String save(WishListRequest wishListRequest) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        if(wishListRepository.existsByProductProductIdAndUserId(wishListRequest.getProductId(), userDetailsCustom.getId())){
            wishListRepository.deleteWishListByProductProductIdAndUserId(wishListRequest.getProductId(), userDetailsCustom.getId());
            return "unlike successfully";
        }
        WishList wishList = WishList.builder()
                .product(productRepository.findById(wishListRequest.getProductId()).orElseThrow(()->new NotFoundException("product not found")))
                .user(userRepository.findById(userDetailsCustom.getId()).orElseThrow(()-> new NotFoundException("user not found")))
                .build();
        wishListRepository.save(wishList);
        return "like successfully";
    }

    @Override
    public List<WishListResponse> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        List<WishList> wishLists = wishListRepository.findAllByUserId(userDetailsCustom.getId());
        return wishLists.stream().map(wishList->WishListResponse.builder()
                .unitPrice(wishList.getProduct().getUnitPrice())
                .productName(wishList.getProduct().getProductName())
                .build()).toList();
    }

    @Override
    public void deleteWishListById(Long id) throws NotFoundException, AccountLockedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        WishList wishList = wishListRepository.findById(id).orElseThrow(()->new NotFoundException("wishlist not found"));
        if (!wishList.getUser().getId().equals(userDetailsCustom.getId()) ){
            throw new AccountLockedException("Unauthorized");
        }
        wishListRepository.delete(wishList);
    }
}
