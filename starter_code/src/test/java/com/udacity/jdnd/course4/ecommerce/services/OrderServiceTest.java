package com.udacity.jdnd.course4.ecommerce.services;

import com.udacity.jdnd.course4.ecommerce.InjectMockUtils;
import com.udacity.jdnd.course4.ecommerce.entities.Cart;
import com.udacity.jdnd.course4.ecommerce.entities.User;
import com.udacity.jdnd.course4.ecommerce.entities.UserOrder;
import com.udacity.jdnd.course4.ecommerce.repository.OrderRepository;
import com.udacity.jdnd.course4.ecommerce.repository.UserRepository;
import com.udacity.jdnd.course4.ecommerce.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    public static final String USERNAME = "thaint11";

    private OrderService orderService;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderService = new OrderService(userRepository, orderRepository);
        InjectMockUtils.inject(orderService, "userRepository", userRepository);
        InjectMockUtils.inject(orderService, "orderRepository", orderRepository);
    }

    @Test
    public void testCreateOrderForInvalidUser() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        ResponseEntity<UserOrder> response = orderService.createOrderForUser(USERNAME);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateOrderForUser() {
        User user = DummyData.getUser();
        Cart cart = DummyData.getCart();
        user.setCart(cart);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ResponseEntity<UserOrder> response = orderService.createOrderForUser(USERNAME);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder responseOrder = response.getBody();
        assertNotNull(responseOrder);
        assertNotNull(responseOrder.getItems());
        assertNotNull(responseOrder.getUser());
    }

    @Test
    public void testGetOrdersForUser() {
        User user = DummyData.getUser();
        Cart cart = DummyData.getCart();
        user.setCart(cart);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        orderService.createOrderForUser(USERNAME);

        ResponseEntity<List<UserOrder>> responseEntity = orderService.getOrdersOfUser(USERNAME);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<UserOrder> userOrders = responseEntity.getBody();
        assertNotNull(userOrders);
    }

    @Test
    public void testGetOrdersForInvalidUser() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        orderService.createOrderForUser(USERNAME);

        ResponseEntity<List<UserOrder>> responseEntity = orderService.getOrdersOfUser(USERNAME);

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }
}