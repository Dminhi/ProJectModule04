package com.example.project.controller.user;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.category.CategoryEditRequest;
import com.example.project.model.dto.request.shoppingcart.CheckOutRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartEditRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartRequest;
import com.example.project.model.dto.response.ShoppingCartResponse;
import com.example.project.model.entity.Orders;
import com.example.project.model.entity.ShoppingCart;
import com.example.project.service.shoppingcart.IShoppingCartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/user/cart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService shoppingCartService;
    @GetMapping("/list")
    public ResponseEntity<List<ShoppingCartResponse>> findAll() {
        List<ShoppingCartResponse> shoppingCarts = shoppingCartService.findAll();
        return new ResponseEntity<>(shoppingCarts, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCart> create(@Valid @RequestBody ShoppingCartRequest shoppingCartRequest) throws NotFoundException {
        ShoppingCart sc = shoppingCartService.save(shoppingCartRequest);
        return new ResponseEntity<>(sc,HttpStatus.CREATED);
    }

    @PutMapping("items/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,@RequestBody ShoppingCartEditRequest shoppingCartEditRequest) throws NotFoundException {
        shoppingCartService.save(shoppingCartEditRequest,id);
        return new ResponseEntity<>("Update Successful",HttpStatus.OK);
    }
    @DeleteMapping("items/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        shoppingCartService.deleteShoppingCartById(id);
        return new ResponseEntity<>("Delete Successful",HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> deleteAll(){
        shoppingCartService.deleteAllShoppingCart();
        return new ResponseEntity<>("Delete All Successful",HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Orders> checkout(@RequestBody CheckOutRequest checkOutRequest) throws NotFoundException {
        Orders orders = shoppingCartService.checkOut(checkOutRequest);
        return new ResponseEntity<>(orders,HttpStatus.CREATED);
    }

}
