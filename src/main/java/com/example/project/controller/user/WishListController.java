package com.example.project.controller.user;

import com.example.project.exception.AccountLockedException;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.category.CategoryRequest;
import com.example.project.model.dto.request.wishlist.WishListRequest;
import com.example.project.model.dto.response.WishListResponse;
import com.example.project.model.entity.Category;
import com.example.project.model.entity.WishList;
import com.example.project.service.wishlist.IWishListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/user/wish-list")
public class WishListController {
    @Autowired
    private IWishListService wishListService;
    @PostMapping()
    public ResponseEntity<String> create(@Valid @RequestBody WishListRequest wishListRequest) throws NotFoundException {
        String wishList = wishListService.save(wishListRequest);
        return new ResponseEntity<>(wishList, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<WishListResponse>> findAll(){
        List<WishListResponse> wishListResponses = wishListService.findAll();
        return new ResponseEntity<>(wishListResponses,HttpStatus.OK);
    }

    @DeleteMapping("/{wishListId}")
    public ResponseEntity<String> deleteWishList(@PathVariable Long wishListId) throws AccountLockedException, NotFoundException {
        wishListService.deleteWishListById(wishListId);
        return new ResponseEntity<>("delete successfully",HttpStatus.OK);
    }
}
