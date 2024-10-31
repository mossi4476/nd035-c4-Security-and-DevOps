package com.udacity.jdnd.course4.ecommerce.service;

import com.udacity.jdnd.course4.ecommerce.dto.request.UserCreateRequest;
import com.udacity.jdnd.course4.ecommerce.entities.Cart;
import com.udacity.jdnd.course4.ecommerce.entities.User;
import com.udacity.jdnd.course4.ecommerce.repository.CartRepository;
import com.udacity.jdnd.course4.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       CartRepository cartRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<User> findById(Long id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    public ResponseEntity<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            logger.info("User not found");
            ResponseEntity.notFound().build();
        }

        logger.info("User exist.");
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> createUser(UserCreateRequest request) {
        User user = new User();
        logger.info("Username = " + request.getUsername());
        user.setUsername(request.getUsername());

        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);

        if (request.getPassword().length() < 6 || !request.getPassword().equals(request.getConfirmPassword())) {
            logger.info("Length of password has to be less than 6 or password has to be different from ConfirmPassword");
            return ResponseEntity.badRequest().build();
        }

        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        logger.info("Create User success");
        return ResponseEntity.ok(user);
    }
}
