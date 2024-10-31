package com.udacity.jdnd.course4.ecommerce.services;

import com.udacity.jdnd.course4.ecommerce.InjectMockUtils;
import com.udacity.jdnd.course4.ecommerce.entities.Item;
import com.udacity.jdnd.course4.ecommerce.repository.ItemRepository;
import com.udacity.jdnd.course4.ecommerce.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.udacity.jdnd.course4.ecommerce.services.DummyData.firstItemName;
import static com.udacity.jdnd.course4.ecommerce.services.DummyData.getFirstItemData;
import static com.udacity.jdnd.course4.ecommerce.services.DummyData.getSecondItemData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemServiceTest {

    private ItemService itemService;

    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemService = new ItemService(itemRepository);
        InjectMockUtils.inject(itemService, "itemRepository", itemRepository);
    }

    @Test
    public void testGetAllItems() {
        List<Item> items = new ArrayList<>(Arrays.asList(getFirstItemData(), getSecondItemData()));
        when(itemRepository.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> response = itemService.getAllItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> responseItem = response.getBody();
        assertNotNull(responseItem);
        assertEquals(2, responseItem.size());
    }

    @Test
    public void testGetItemById() {
        Item item = getFirstItemData();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ResponseEntity<Item> response = itemService.getItemById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item responseItem = response.getBody();
        assertEquals(item, responseItem);
        assertNotNull(responseItem);
        assertEquals(item.getName(), responseItem.getName());
        assertEquals(item.getDescription(), responseItem.getDescription());
    }

    @Test
    public void testGetItemsByName() {
        Item item = getSecondItemData();
        List<Item> items = new ArrayList<>(Collections.singletonList(item));
        when(itemRepository.findByName(firstItemName)).thenReturn(items);

        ResponseEntity<List<Item>> response = itemService.getItemsByName(firstItemName);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> responseItem = response.getBody();
        assertNotNull(responseItem);
        assertEquals(item, responseItem.get(0));
    }
}