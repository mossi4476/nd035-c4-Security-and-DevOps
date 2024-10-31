package com.udacity.jdnd.course4.ecommerce.security;

import com.udacity.jdnd.course4.ecommerce.services.DummyData;
import com.udacity.jdnd.course4.ecommerce.entities.User;
import com.udacity.jdnd.course4.ecommerce.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Before
    public void setUp() {
        userDetailsServiceImpl = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = DummyData.getUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());
        assertNotNull(userDetails);
        Collection<? extends GrantedAuthority> authorityCollection = userDetails.getAuthorities();
        assertNotNull(authorityCollection);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }
}