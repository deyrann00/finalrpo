package com.example.finalrpo.service;

import com.example.finalrpo.dto.SignUpRequestDTO;
import com.example.finalrpo.dto.UserDTO;
import com.example.finalrpo.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User signUp(String email, String password, String fullName, Long departmentId);
    User getCurrentUser();
    void changePassword(String oldPassword, String newPassword);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUserByAdmin(SignUpRequestDTO request);
    void blockUser(Long id);
    void deleteUser(Long id);
    void setUserActiveStatus(Long id, boolean status);
    UserDTO updateUser(Long id, SignUpRequestDTO request);
}
