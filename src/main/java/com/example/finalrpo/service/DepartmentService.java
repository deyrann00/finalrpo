package com.example.finalrpo.service;

import com.example.finalrpo.dto.DepartmentDTO;
import com.example.finalrpo.dto.DepartmentRequestDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentRequestDTO departmentDto);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartment(Long id);
    DepartmentDTO updateDepartment(Long id, DepartmentRequestDTO departmentDto);
    void deleteDepartment(Long id);
}
