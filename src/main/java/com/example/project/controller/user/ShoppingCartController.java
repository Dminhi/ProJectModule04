package com.example.project.controller.user;

import com.example.project.exception.DataNotFound;
import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.request.category.CategoryEditRequest;
import com.example.project.model.dto.request.shoppingcart.CheckOutRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartEditRequest;
import com.example.project.model.dto.request.shoppingcart.ShoppingCartRequest;
import com.example.project.model.dto.response.OrderResponse;
import com.example.project.model.dto.response.ProductResponse;
import com.example.project.model.dto.response.ShoppingCartResponse;
import com.example.project.model.dto.responsewapper.EHttpStatus;
import com.example.project.model.dto.responsewapper.ResponseWapper;
import com.example.project.model.entity.Orders;
import com.example.project.model.entity.Product;
import com.example.project.model.entity.ShoppingCart;
import com.example.project.service.product.IProductService;
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

    @Autowired
    private IProductService productService;
    @GetMapping("/list")
    public ResponseEntity<?> findAll() throws NotFoundException, DataNotFound {
        List<ShoppingCartResponse> shoppingCartResponses = shoppingCartService.findAllByUserId();
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                shoppingCartResponses), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@Valid @RequestBody ShoppingCartRequest shoppingCartRequest) throws NotFoundException {
        ShoppingCartResponse sc = shoppingCartService.save(shoppingCartRequest);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                sc), HttpStatus.CREATED);
    }

    @PutMapping("items/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody ShoppingCartEditRequest shoppingCartEditRequest) throws NotFoundException {
        ShoppingCartResponse shoppingCartResponse = shoppingCartService.save(shoppingCartEditRequest,id);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                shoppingCartResponse), HttpStatus.OK);
    }
    @DeleteMapping("items/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws NotFoundException {
        shoppingCartService.deleteShoppingCartById(id);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                "delete successful"), HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> deleteAll() throws NotFoundException, DataNotFound {
        shoppingCartService.deleteAllShoppingCart();
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                "delete all successful"), HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckOutRequest checkOutRequest) throws NotFoundException, DataNotFound {
        Orders orders = shoppingCartService.checkOut(checkOutRequest);
        return new ResponseEntity<>(new ResponseWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                orders), HttpStatus.OK);
    }

}
