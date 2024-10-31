package com.udacity.jdnd.course4.ecommerce.services;

import com.udacity.jdnd.course4.ecommerce.InjectMockUtils;
import com.udacity.jdnd.course4.ecommerce.dto.request.UserCreateRequest;
import com.udacity.jdnd.course4.ecommerce.entities.User;
import com.udacity.jdnd.course4.ecommerce.repository.CartRepository;
import com.udacity.jdnd.course4.ecommerce.repository.UserRepository;
import com.udacity.jdnd.course4.ecommerce.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    public static final String USER_NAME = "ThaiNT11";
    public static final String PASSWORD = "Password";
    public static final String IS_HASHED = "isHashed";

    private UserService userService;

    private final UserRepository userRepository = mock(UserRepository.class);

    private final CartRepository cartRepository = mock(CartRepository.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userService = new UserService(userRepository, cartRepository, bCryptPasswordEncoder);
        InjectMockUtils.inject(userService, "userRepository", userRepository);
        InjectMockUtils.inject(userService, "cartRepository", cartRepository);
        InjectMockUtils.inject(userService, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void testCreateUserSuccess() {
        when(bCryptPasswordEncoder.encode(PASSWORD)).thenReturn(IS_HASHED);

        UserCreateRequest requestCreate = new UserCreateRequest();
        requestCreate.setUsername(USER_NAME);
        requestCreate.setUsername(USER_NAME);
        requestCreate.setPassword("Password");
        requestCreate.setConfirmPassword("Password");

        final ResponseEntity<User> response = userService.createUser(requestCreate);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(USER_NAME, user.getUsername());
        assertEquals(IS_HASHED, user.getPassword());
    }

    @Test
    public void testFindUserById() {
        when(bCryptPasswordEncoder.encode(PASSWORD)).thenReturn(IS_HASHED);

        UserCreateRequest requestCreate = new UserCreateRequest();
        requestCreate.setUsername(USER_NAME);
        requestCreate.setPassword(PASSWORD);
        requestCreate.setConfirmPassword(PASSWORD);

        final ResponseEntity<User> response = userService.createUser(requestCreate);
        User user = response.getBody();
        when(userRepository.findById(0L)).thenReturn(Optional.ofNullable(user));

        final ResponseEntity<User> userResponse = userService.findById(0L);
        User userResponseData = userResponse.getBody();
        assertNotNull(userResponseData);
        assertEquals(USER_NAME, userResponseData.getUsername());
        assertEquals(IS_HASHED, userResponseData.getPassword());
    }

    @Test
    public void testFindByUsername() {
        when(bCryptPasswordEncoder.encode(PASSWORD)).thenReturn(IS_HASHED);

        UserCreateRequest requestCreate = new UserCreateRequest();
        requestCreate.setUsername(USER_NAME);
        requestCreate.setPassword(PASSWORD);
        requestCreate.setConfirmPassword(PASSWORD);

        final ResponseEntity<User> response = userService.createUser(requestCreate);
        User user = response.getBody();
        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);

        final ResponseEntity<User> userResponse = userService.findByUsername(USER_NAME);
        User userResponseData = userResponse.getBody();
        assertNotNull(userResponseData);
        assertEquals(USER_NAME, userResponseData.getUsername());
        assertEquals(IS_HASHED, userResponseData.getPassword());
    }
}