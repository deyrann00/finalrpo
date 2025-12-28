package com.example.finalrpo.service.impl;

import com.example.finalrpo.dto.SignUpRequestDTO;
import com.example.finalrpo.dto.UserDTO;
import com.example.finalrpo.mapper.UserMapper;
import com.example.finalrpo.models.User;
import com.example.finalrpo.models.Department;
import com.example.finalrpo.repository.UserRepository;
import com.example.finalrpo.repository.RoleRepository;
import com.example.finalrpo.repository.DepartmentRepository;
import com.example.finalrpo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .disabled(!user.isActive()) // <--- THIS BLOCKS THE LOGIN IF ACTIVE IS FALSE
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole()))
                        .toList())
                .build();
    }

    @Override
    public User signUp(String email, String password, String fullName, Long departmentId) {
        User u = new User();
        u.setEmail(email);
        u.setFullName(fullName);
        u.setPassword(passwordEncoder.encode(password));

        Department dept = departmentRepository.findById(departmentId).orElse(null);
        u.setDepartment(dept);

        u.setRoles(List.of(roleRepository.findByRole("ROLE_USER")));

        return userRepository.save(u);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {

            String email = authentication.getName();
            return userRepository.findByEmail(email);
        }
        return null;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        User currentUser = getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("You must be logged in to change your password");
        }

        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(currentUser);
        } else {
            throw new RuntimeException("The old password you entered is incorrect");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO createUserByAdmin(SignUpRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        User u = new User();
        u.setEmail(request.getEmail());
        u.setFullName(request.getFullName());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setActive(true);

        Department dept = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        u.setDepartment(dept);

        // Assign default user role or customize as needed
        u.setRoles(List.of(roleRepository.findByRole("ROLE_USER")));

        User savedUser = userRepository.save(u);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void blockUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void setUserActiveStatus(Long id, boolean status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setActive(status);
        userRepository.save(user);

        System.out.println("User " + user.getEmail() + " active status set to: " + status);
    }

    @Override
    public UserDTO updateUser(Long id, SignUpRequestDTO request) {
        // 1. Find the existing user
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 2. Update basic fields
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());

        // 3. Update password only if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // 4. Update Department
        if (request.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            user.setDepartment(dept);
        }

        // 5. Save and return as DTO
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }
}
