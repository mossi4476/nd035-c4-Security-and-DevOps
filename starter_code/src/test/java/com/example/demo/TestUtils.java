package com.example.demo;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.requests.CreateUserRequest;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class TestUtils {

    public static String USERNAME = "tuanlv";
    public static String PASSWORD = "123456aA@";
    public static String PASSWORD_HASH = "123456aA@_had_been_hash";
    public static String CONFIRM_PASSWORD_SUCCESS = "123456aA@";
    public static String CONFIRM_PASSWORD_FAIL = "123123123";


    public static void injectObjects(Object target, String fieldName, Object toInject) {
        boolean wasPrivate = false;

        try {
            Field  f = target.getClass().getDeclaredField(fieldName);
            if (!f.isAccessible()) {
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);

            if (wasPrivate) {
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static CreateUserRequest initUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(TestUtils.USERNAME);
        createUserRequest.setPassword(TestUtils.PASSWORD);
        createUserRequest.setConfirmPassword(TestUtils.CONFIRM_PASSWORD_SUCCESS);
        return createUserRequest;
    }

    public static Item getItem0() {
        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");
        return item;
    }

    public static Item getItem1() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Square Widget");
        item.setPrice(new BigDecimal("1.99"));
        item.setDescription("A widget that is square");
        return item;
    }
}
