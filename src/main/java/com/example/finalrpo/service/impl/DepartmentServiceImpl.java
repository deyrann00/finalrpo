package com.example.finalrpo.service.impl;

import com.example.finalrpo.dto.DepartmentDTO;
import com.example.finalrpo.dto.DepartmentRequestDTO;
import com.example.finalrpo.mapper.DepartmentMapper;
import com.example.finalrpo.models.Department;
import com.example.finalrpo.repository.DepartmentRepository;
import com.example.finalrpo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDTO createDepartment(DepartmentRequestDTO departmentDto) {
        Department department = departmentMapper.toEntity(departmentDto);

        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toDto(savedDepartment);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toDtoList(departments);
    }

    @Override
    public DepartmentDTO getDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentRequestDTO departmentDto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        department.setName(departmentDto.getName());

        return departmentMapper.toDto(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
