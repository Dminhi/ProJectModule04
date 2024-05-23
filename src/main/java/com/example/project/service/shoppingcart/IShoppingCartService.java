package com.example.project.service.shoppingcart;

import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.shoppingcart.CheckOutRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartEditRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartRequest;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.dto.response.ShoppingCartResponse;
import com.example.project.model.entity.Orders;
import com.example.project.model.entity.ShoppingCart;
import com.example.project.repository.IShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShoppingCartService{
    List<ShoppingCartResponse> findAll();
    ShoppingCartResponse save(ShoppingCartRequest shoppingCartRequest) throws NotFoundException;

    ShoppingCartResponse save(ShoppingCartEditRequest shoppingCartEditRequest,Long id) throws NotFoundException;

    void deleteShoppingCartById(Long id) throws NotFoundException;
    void deleteAllShoppingCart() throws NotFoundException, DataNotFound;
    Orders checkOut(CheckOutRequest checkOutRequest) throws NotFoundException, DataNotFound;
    List<ShoppingCartResponse> findAllByUserId() throws DataNotFound;
}
