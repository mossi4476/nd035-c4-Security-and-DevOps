package com.udacity.jdnd.course4.ecommerce.persistence;

import com.udacity.jdnd.course4.ecommerce.services.DummyData;
import com.udacity.jdnd.course4.ecommerce.entities.Item;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemTest {

    @Test
    public void testEqualsItem() {
        Item item1 = new Item();
        item1.setId(2L);
        Item item2 = new Item();
        item2.setId(2L);

        assertEquals(item2.getId(), item1.getId());
        assertTrue(item1.getId() == item2.getId());
    }

    @Test
    public void testNotEqualsItem() {
        Item item1 = DummyData.getFirstItemData();
        assertNotEquals(item1, null);
        Item item2 = DummyData.getSecondItemData();

        assertNotEquals(item2, null);
        assertNotEquals(item1, item2);
    }
}