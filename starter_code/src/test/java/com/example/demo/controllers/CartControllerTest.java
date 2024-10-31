package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartControllerTest {

    private CartController cartController;
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private final ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addTocart() {
        User initUser = new User();
        initUser.setUsername(TestUtils.USERNAME);
        List<Item> initItems = new ArrayList<>();
        Item item0 = TestUtils.getItem0();
        initItems.add(item0);

        Cart cart = new Cart();
        cart.setId(0L);
        cart.setItems(initItems);
        cart.setTotal(new BigDecimal("2.99"));
        cart.setUser(initUser);
        initUser.setCart(cart);

        Mockito.when(userRepository.findByUsername(TestUtils.USERNAME)).thenReturn(initUser);
        Mockito.when(itemRepository.findById(item0.getId())).thenReturn(java.util.Optional.of(item0));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(item0.getId());
        request.setQuantity(1);
        request.setUsername(TestUtils.USERNAME);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Cart retrievedCart = response.getBody();
        assertNotNull(retrievedCart);
        assertNotNull(retrievedCart.getId());
        List<Item> items = retrievedCart.getItems();
        assertNotNull(items);
        Item retrievedItem = items.get(0);
        assertEquals(2, items.size());
        assertNotNull(retrievedItem);
        assertEquals(item0, retrievedItem);
        assertEquals(new BigDecimal("5.98"), retrievedCart.getTotal());
        assertEquals(initUser, retrievedCart.getUser());
    }

    @Test
    public void removeFromcart() {
        User initUser = new User();
        initUser.setUsername(TestUtils.USERNAME);
        List<Item> initItems = new ArrayList<>();
        Item item0 = TestUtils.getItem0();
        initItems.add(item0);

        Cart cart = new Cart();
        cart.setId(0L);
        cart.setItems(initItems);
        cart.setTotal(new BigDecimal("2.99"));
        cart.setUser(initUser);
        initUser.setCart(cart);

        Mockito.when(userRepository.findByUsername(TestUtils.USERNAME)).thenReturn(initUser);
        Mockito.when(itemRepository.findById(item0.getId())).thenReturn(java.util.Optional.of(item0));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(item0.getId());
        request.setQuantity(1);
        request.setUsername(TestUtils.USERNAME);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart retrievedCart = response.getBody();
        assertNotNull(retrievedCart);
        assertNotNull(retrievedCart.getId());
        List<Item> items = retrievedCart.getItems();
        assertNotNull(items);
        assertEquals(0, items.size());
        assertEquals(new BigDecimal("0.00"), retrievedCart.getTotal());
        assertEquals(initUser, retrievedCart.getUser());
    }
}