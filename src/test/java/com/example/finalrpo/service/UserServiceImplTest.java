package com.example.finalrpo.service;

import com.example.finalrpo.models.User;
import com.example.finalrpo.repository.RoleRepository;
import com.example.finalrpo.repository.UserRepository;
import com.example.finalrpo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSignUp_PasswordHashing() {
        // 1. Setup
        String rawPassword = "mySecretPassword";
        String encodedPassword = "encoded_mySecretPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]); // Return what is passed

        // 2. Execute
        User createdUser = userService.signUp("new@user.com", rawPassword, "New User", null);

        // 3. Verify
        Assertions.assertEquals(encodedPassword, createdUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }
}
