package com.example.finalrpo.service;

import com.example.finalrpo.models.Department;
import com.example.finalrpo.models.Role;
import com.example.finalrpo.models.User;
import com.example.finalrpo.repository.DepartmentRepository;
import com.example.finalrpo.repository.RoleRepository;
import com.example.finalrpo.repository.UserRepository;
import com.example.finalrpo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class) // This initializes the mocks
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentRepository departmentRepository; // This was null!

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService; // Mocks are injected here

    @Test
    public void testSignUp_PasswordHashing() {
        // 1. Create a dummy role to return
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");

        // 2. Mock the Department lookup (as done previously)
        when(departmentRepository.findById(any())).thenReturn(Optional.of(new Department()));

        // 3. FIX: Mock the Role lookup to prevent the NPE at List.of()
        when(roleRepository.findByRole("ROLE_USER")).thenReturn(userRole);

        // 4. Mock password encoding and user saving
        when(passwordEncoder.encode(any())).thenReturn("hashed_password");
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // 5. Execute the test
        User result = userService.signUp("test@email.com", "123", "Full Name", 1L);

        assertNotNull(result);
        assertNotNull(result.getRoles());
        assertEquals("ROLE_USER", result.getRoles().get(0).getRole());
    }
}