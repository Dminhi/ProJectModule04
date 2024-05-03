package com.example.project.service.wishlist;

import com.example.project.exception.AccountLockedException;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.wishlist.WishListRequest;
import com.example.project.model.dto.response.WishListResponse;
import com.example.project.model.entity.WishList;
import io.grpc.lb.v1.LoadBalancerGrpc;

import java.util.List;

public interface IWishListService {
    String save(WishListRequest wishListRequest) throws NotFoundException;
    List<WishListResponse> findAll();
    void deleteWishListById(Long id) throws NotFoundException, AccountLockedException;
}
