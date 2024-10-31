package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemControllerTest {

    private ItemController itemController;
    private final ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getItems() {
        List<Item> initItems = new ArrayList<>();
        Item item0 = TestUtils.getItem0();
        Item item1 = TestUtils.getItem1();
        initItems.add(item0);
        initItems.add(item1);
        Mockito.when(itemRepository.findAll()).thenReturn(initItems);

        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals(item0, items.get(0));
        assertEquals(item1, items.get(1));

    }

    @Test
    public void getItemById() {
        Item item0 = TestUtils.getItem0();
        Mockito.when(itemRepository.findById(item0.getId())).thenReturn(java.util.Optional.of(item0));

        ResponseEntity<Item> response = itemController.getItemById(item0.getId());
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Item item = response.getBody();
        assertNotNull(item);
        assertEquals(item0, item);
    }

    @Test
    public void getItemsByName() {
        List<Item> initItems = new ArrayList<>();
        Item item0 = TestUtils.getItem0();
        initItems.add(item0);
        Mockito.when(itemRepository.findByName(item0.getName())).thenReturn(initItems);

        ResponseEntity<List<Item>> response = itemController.getItemsByName(item0.getName());
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(item0, items.get(0));
    }
}