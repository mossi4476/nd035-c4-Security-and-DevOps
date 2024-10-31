package com.udacity.jdnd.course4.ecommerce.services;

import com.udacity.jdnd.course4.ecommerce.InjectMockUtils;
import com.udacity.jdnd.course4.ecommerce.dto.request.CartModifyRequest;
import com.udacity.jdnd.course4.ecommerce.entities.Cart;
import com.udacity.jdnd.course4.ecommerce.entities.Item;
import com.udacity.jdnd.course4.ecommerce.entities.User;
import com.udacity.jdnd.course4.ecommerce.repository.CartRepository;
import com.udacity.jdnd.course4.ecommerce.repository.ItemRepository;
import com.udacity.jdnd.course4.ecommerce.repository.UserRepository;
import com.udacity.jdnd.course4.ecommerce.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    public static final String USERNAME = "thaint11";

    private CartService cartService;

    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp() {
        cartService = new CartService(userRepository, cartRepository, itemRepository);
        InjectMockUtils.inject(cartService, "userRepository", userRepository);
        InjectMockUtils.inject(cartService, "userRepository", userRepository);
        InjectMockUtils.inject(cartService, "itemRepository", itemRepository);
    }

    @Test
    public void testAddItemToCartOfInvalidUser() {
        User user = DummyData.getUser();
        Item item = DummyData.getSecondItemData();
        Cart cart = DummyData.getCart();
        user.setCart(cart);

        when(userRepository.findByUsername(USERNAME)).thenReturn(null);
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));

        CartModifyRequest request = new CartModifyRequest();
        request.setItemId(2L);
        request.setQuantity(2);
        request.setUsername(USERNAME);

        ResponseEntity<Cart> response = cartService.addItemToCart(request);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testAddItemToCart() {
        User user = DummyData.getUser();
        Item item = DummyData.getSecondItemData();
        Cart cart = DummyData.getCart();
        user.setCart(cart);

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));

        CartModifyRequest request = new CartModifyRequest();
        request.setItemId(2L);
        request.setQuantity(2);
        request.setUsername(USERNAME);

        ResponseEntity<Cart> response = cartService.addItemToCart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart responseCart = response.getBody();
        assertNotNull(responseCart);
        assertEquals(1L, responseCart.getId());

        List<Item> items = responseCart.getItems();
        assertNotNull(items);
        assertEquals(4, items.size());
        assertEquals(item, items.get(2));
        assertEquals(item.getPrice(), items.get(2).getPrice());
        assertNotEquals(user.getCart(), responseCart.getUser().getCart());
    }

    @Test
    public void removeItemFromCart() {
        User user = DummyData.getUser();
        Item item = DummyData.getSecondItemData();
        Cart cart = DummyData.getCart();
        user.setCart(cart);

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));

        CartModifyRequest request = new CartModifyRequest();
        request.setItemId(2L);
        request.setQuantity(1);
        request.setUsername(USERNAME);

        ResponseEntity<Cart> response = cartService.removeFromCart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart responseCart = response.getBody();
        assertNotNull(responseCart);
        assertEquals(1L, responseCart.getId());

        List<Item> items = responseCart.getItems();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(new BigDecimal("999"), responseCart.getTotal());
        assertNotEquals(user.getCart(), responseCart.getUser().getCart());
    }
}