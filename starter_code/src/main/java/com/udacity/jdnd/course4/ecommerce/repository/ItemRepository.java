package com.udacity.jdnd.course4.ecommerce.repository;

import com.udacity.jdnd.course4.ecommerce.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByName(String name);
}
