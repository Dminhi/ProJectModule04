package com.example.project.service.shoppingcart;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.shoppingcart.CheckOutRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartEditRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartRequest;
import com.example.project.model.dto.response.ShoppingCartResponse;
import com.example.project.model.entity.Orders;
import com.example.project.model.entity.ShoppingCart;
import com.example.project.repository.IShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShoppingCartService{
    List<ShoppingCartResponse> findAll();
    ShoppingCart save(ShoppingCartRequest shoppingCartRequest) throws NotFoundException;

    ShoppingCart save(ShoppingCartEditRequest shoppingCartEditRequest,Long id) throws NotFoundException;

    void deleteShoppingCartById(Long id);
    void deleteAllShoppingCart();
    Orders checkOut(CheckOutRequest checkOutRequest) throws NotFoundException;
}
