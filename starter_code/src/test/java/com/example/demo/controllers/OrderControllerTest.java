package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class OrderControllerTest {

    private OrderController orderController;
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        com.example.demo.TestUtils.injectObjects(orderController, "userRepository", userRepository);
        com.example.demo.TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void submit() {
        User user = new User();
        user.setUsername(TestUtils.USERNAME);
        user.setPassword(TestUtils.PASSWORD);
        user.setId(0L);
        Item item = TestUtils.getItem0();
        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99"));
        cart.setUser(user);
        user.setCart(cart);
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        ResponseEntity<UserOrder> response =  orderController.submit(user.getUsername());

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        UserOrder retrievedUserOrder = response.getBody();
        assertNotNull(retrievedUserOrder);
        assertNotNull(retrievedUserOrder.getItems());
        assertNotNull(retrievedUserOrder.getTotal());
        assertNotNull(retrievedUserOrder.getUser());
    }

    @Test
    public void getOrdersForUser() {
        User user = new User();
        user.setUsername(TestUtils.USERNAME);
        user.setPassword(TestUtils.PASSWORD);
        user.setId(0L);
        Item item = TestUtils.getItem0();
        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99"));
        cart.setUser(user);
        user.setCart(cart);
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        orderController.submit(user.getUsername());

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(user.getUsername());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        List<UserOrder> userOrders = responseEntity.getBody();
        assertNotNull(userOrders);
        assertEquals(0, userOrders.size());
    }
}