package com.udacity.jdnd.course4.ecommerce.repository;

import com.udacity.jdnd.course4.ecommerce.entities.Cart;
import com.udacity.jdnd.course4.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
