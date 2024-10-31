package com.udacity.jdnd.course4.ecommerce.service;

import com.udacity.jdnd.course4.ecommerce.entities.Item;
import com.udacity.jdnd.course4.ecommerce.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    public ResponseEntity<Item> getItemById(Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (!itemOptional.isPresent()) {
            logger.error("Item not found.");
            return ResponseEntity.notFound().build();
        }

        logger.info("Get item success.");
        return ResponseEntity.ok(itemOptional.get());
    }

    public ResponseEntity<List<Item>> getItemsByName(String name) {
        List<Item> itemList = itemRepository.findByName(name);
        if (CollectionUtils.isEmpty(itemList)) {
            logger.error("No items found.");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Get Items success.");
            return ResponseEntity.ok(itemList);
        }
    }
}
