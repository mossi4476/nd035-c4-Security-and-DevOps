package com.udacity.jdnd.course4.ecommerce.controller;

import com.udacity.jdnd.course4.ecommerce.dto.request.CartModifyRequest;
import com.udacity.jdnd.course4.ecommerce.entities.Cart;
import com.udacity.jdnd.course4.ecommerce.service.CartService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody CartModifyRequest request) {
        return cartService.addItemToCart(request);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@RequestBody CartModifyRequest request) {
        return cartService.removeFromCart(request);
    }
}
