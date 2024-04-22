package com.creative.hfs.hfsbackend.service;

import static org.mockito.Mockito.when;

import com.creative.hfs.hfsbackend.model.entity.User;
import com.creative.hfs.hfsbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername_WhenUserExists() {
        // Mock data
        User user = new User();
        user.setUserId(11200);
        user.setPassword("password11200");

        // Mock repository response
        when(userRepository.findByUserId(11200)).thenReturn(user);

        // Call the service method
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("11200");

        // Verify the result
        assertEquals("11200", userDetails.getUsername());
        assertEquals("password11200", userDetails.getPassword());
    }

}
