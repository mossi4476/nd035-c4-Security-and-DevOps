package com.udacity.jdnd.course4.ecommerce.repository;

import com.udacity.jdnd.course4.ecommerce.entities.User;
import com.udacity.jdnd.course4.ecommerce.entities.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {

    List<UserOrder> findByUser(User user);
}
