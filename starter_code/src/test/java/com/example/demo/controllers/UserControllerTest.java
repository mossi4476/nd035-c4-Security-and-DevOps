package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.Assert.*;

public class UserControllerTest {

    private UserController userController;
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findById() {

        CreateUserRequest createUserRequest = TestUtils.initUser();
        Mockito.when(bCryptPasswordEncoder.encode(TestUtils.PASSWORD)).thenReturn(TestUtils.PASSWORD_HASH);

        ResponseEntity<User> response = userController.createUser(createUserRequest);
        User userCreated = response.getBody();
        Mockito.when(userRepository.findById(userCreated.getId())).thenReturn(java.util.Optional.ofNullable(userCreated));

        ResponseEntity<User> userResponseEntity = userController.findById(userCreated.getId());
        User user = userResponseEntity.getBody();

        assertNotNull(user);
        assertEquals(user.getId(), userCreated.getId());
        assertEquals(user.getUsername(), userCreated.getUsername());
        assertEquals(user.getPassword(), TestUtils.PASSWORD_HASH);
    }

    @Test
    public void findByUserName() {

        CreateUserRequest createUserRequest = TestUtils.initUser();
        Mockito.when(bCryptPasswordEncoder.encode(TestUtils.PASSWORD)).thenReturn(TestUtils.PASSWORD_HASH);

        ResponseEntity<User> response = userController.createUser(createUserRequest);
        User userCreated = response.getBody();
        Mockito.when(userRepository.findByUsername(userCreated.getUsername())).thenReturn(userCreated);

        ResponseEntity<User> userResponseEntity = userController.findByUserName(userCreated.getUsername());
        User user = userResponseEntity.getBody();

        assertNotNull(user);
        assertEquals(user.getId(), userCreated.getId());
        assertEquals(user.getUsername(), userCreated.getUsername());
        assertEquals(user.getPassword(), TestUtils.PASSWORD_HASH);
    }

    @Test
    public void createUserSuccess() {
        CreateUserRequest createUserRequest = TestUtils.initUser();
        Mockito.when(bCryptPasswordEncoder.encode(TestUtils.PASSWORD)).thenReturn(TestUtils.PASSWORD_HASH);

        ResponseEntity<User> userCreated = userController.createUser(createUserRequest);

        assertNotNull(userCreated);
        assertEquals(HttpStatus.OK.value(), userCreated.getStatusCodeValue());
        User user = userCreated.getBody();
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(user.getUsername(), TestUtils.USERNAME);
        assertEquals(user.getPassword(), TestUtils.PASSWORD_HASH);
    }


    @Test
    public void createUserFail() {
        CreateUserRequest createUserRequest = TestUtils.initUser();
        //replace CONFIRM_PASSWORD error
        createUserRequest.setConfirmPassword(TestUtils.CONFIRM_PASSWORD_FAIL);
        Mockito.when(bCryptPasswordEncoder.encode(TestUtils.PASSWORD)).thenReturn(TestUtils.PASSWORD_HASH);

        ResponseEntity<User> userCreated = userController.createUser(createUserRequest);
        assertEquals(HttpStatus.BAD_REQUEST.value(), userCreated.getStatusCodeValue());
    }
}