package com.example.finalrpo.controller;

import com.example.finalrpo.dto.DepartmentDTO;
import com.example.finalrpo.dto.DepartmentRequestDTO;
import com.example.finalrpo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // 1. READ ALL
    @GetMapping
    public List<DepartmentDTO> getAll() {
        return departmentService.getAllDepartments();
    }

    // 2. READ ONE
    @GetMapping("/{id}")
    public DepartmentDTO getById(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    // 3. CREATE (Admin Only)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DepartmentDTO create(@RequestBody DepartmentRequestDTO request) {
        return departmentService.createDepartment(request);
    }

    // 4. UPDATE (Admin Only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DepartmentDTO update(@PathVariable Long id, @RequestBody DepartmentRequestDTO request) {
        return departmentService.updateDepartment(id, request);
    }

    // 5. DELETE (Admin Only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}