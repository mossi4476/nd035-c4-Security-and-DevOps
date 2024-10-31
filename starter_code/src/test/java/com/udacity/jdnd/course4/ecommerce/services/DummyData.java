package com.udacity.jdnd.course4.ecommerce.services;

import com.udacity.jdnd.course4.ecommerce.entities.Cart;
import com.udacity.jdnd.course4.ecommerce.entities.Item;
import com.udacity.jdnd.course4.ecommerce.entities.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class DummyData {

    public static String firstItemName = "Laptop Dell";
    public static String secondItemName = "ThinkPad";

    public static Item getFirstItemData() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Laptop Dell");
        item.setPrice(new BigDecimal("999"));
        item.setDescription("A new Iphone");

        return item;
    }

    public static Item getSecondItemData() {
        Item item = new Item();
        item.setId(2L);
        item.setName("ThinkPad");
        item.setPrice(new BigDecimal("1999"));
        item.setDescription("A Legendary.");

        return item;
    }

    public static Cart getCart() {
        Item item1 = getFirstItemData();
        Item item2 = getSecondItemData();

        User user = new User();
        user.setId(1);
        user.setPassword("abc1234");
        user.setUsername("thaint11");

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(new ArrayList<>(Arrays.asList(item1, item2)));
        cart.setTotal(new BigDecimal("2998"));

        return cart;
    }
    public static User getUser() {
        String username = "thaint11";
        User user = new User();
        user.setUsername(username);
        String password = "password";
        user.setPassword(password);
        user.setId(999L);

        return user;
    }
}
