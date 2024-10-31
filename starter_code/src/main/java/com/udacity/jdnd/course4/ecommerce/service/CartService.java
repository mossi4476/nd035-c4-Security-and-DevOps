package com.udacity.jdnd.course4.ecommerce.service;

import com.udacity.jdnd.course4.ecommerce.dto.request.CartModifyRequest;
import com.udacity.jdnd.course4.ecommerce.entities.Cart;
import com.udacity.jdnd.course4.ecommerce.entities.Item;
import com.udacity.jdnd.course4.ecommerce.entities.User;
import com.udacity.jdnd.course4.ecommerce.repository.CartRepository;
import com.udacity.jdnd.course4.ecommerce.repository.ItemRepository;
import com.udacity.jdnd.course4.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class CartService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final ItemRepository itemRepository;

    public CartService(UserRepository userRepository, CartRepository cartRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public ResponseEntity<Cart> addItemToCart(CartModifyRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(Objects.isNull(user)) {
            logger.error("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Item> item = itemRepository.findById(request.getItemId());
        if(!item.isPresent()) {
            logger.error("Item not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                 .forEach(i -> cart.addItem(item.get()));

        cartRepository.save(cart);
        logger.info("Add item to cart success");
        return ResponseEntity.ok(cart);
    }

    public ResponseEntity<Cart> removeFromCart(CartModifyRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(Objects.isNull(user)) {
            logger.error("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Item> item = itemRepository.findById(request.getItemId());
        if(!item.isPresent()) {
            logger.error("Item not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                 .forEach(i -> cart.removeItem(item.get()));

        cartRepository.save(cart);
        logger.info("Remove item from cart success");
        return ResponseEntity.ok(cart);
    }
}
