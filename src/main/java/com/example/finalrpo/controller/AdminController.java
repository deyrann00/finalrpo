package com.example.finalrpo.controller;

import com.example.finalrpo.dto.SignUpRequestDTO;
import com.example.finalrpo.dto.UserDTO;
import com.example.finalrpo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserServiceImpl userService;

    // 1. GET ALL USERS
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // 2. GET ONE USER
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // 3. CREATE USER (Admin creates an employee)
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody SignUpRequestDTO request) {
        UserDTO newUser = userService.createUserByAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // 4. BLOCK/DEACTIVATE USER
    @PutMapping("/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been blocked.");
    }

    // 5. DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Logic to block or unblock
    @PutMapping("/{id}/active")
    public ResponseEntity<String> toggleUserActive(@PathVariable Long id, @RequestParam boolean status) {
        userService.setUserActiveStatus(id, status);
        return ResponseEntity.ok("User status updated to: " + (status ? "Active" : "Blocked"));
    }

    @PutMapping("/{id}") // UPDATE (Full update)
    public UserDTO updateUser(@PathVariable Long id, @RequestBody SignUpRequestDTO request) {
        return userService.updateUser(id, request);
    }
}