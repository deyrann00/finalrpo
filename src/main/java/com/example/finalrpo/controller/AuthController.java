package com.example.finalrpo.controller;

import com.example.finalrpo.dto.LoginRequestDTO;
import com.example.finalrpo.dto.PasswordChangeDTO;
import com.example.finalrpo.dto.SignUpRequestDTO;
import com.example.finalrpo.dto.UserDTO;
import com.example.finalrpo.mapper.UserMapper;
import com.example.finalrpo.models.User;
import com.example.finalrpo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("User signed-in successfully!");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO request) {
        User newUser = userService.signUp(
                request.getEmail(),
                request.getPassword(),
                request.getFullName(),
                request.getDepartmentId()
        );

        if (newUser != null) {
            UserDTO userResponse = userMapper.toDto(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Email already exists or invalid data");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO request) {
        try {
            userService.changePassword(request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}