package com.example.finalrpo.service;

import com.example.finalrpo.dto.DepartmentDTO;
import com.example.finalrpo.dto.DepartmentRequestDTO;
import com.example.finalrpo.mapper.DepartmentMapper;
import com.example.finalrpo.models.Department;
import com.example.finalrpo.repository.DepartmentRepository;
import com.example.finalrpo.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void testCreateDepartment() {
        // 1. SETUP
        DepartmentRequestDTO inputDto = new DepartmentRequestDTO();
        inputDto.setName("IT Services");

        Department entityToSave = new Department();
        entityToSave.setName("IT Services");

        Department savedEntity = new Department();
        savedEntity.setId(10L);
        savedEntity.setName("IT Services");

        DepartmentDTO outputDto = new DepartmentDTO();
        outputDto.setId(10L);
        outputDto.setName("IT Services");

        // 2. MOCK
        when(departmentMapper.toEntity(inputDto)).thenReturn(entityToSave);
        when(departmentRepository.save(entityToSave)).thenReturn(savedEntity);
        when(departmentMapper.toDto(savedEntity)).thenReturn(outputDto);

        // 3. EXECUTE
        DepartmentDTO result = departmentService.createDepartment(inputDto);

        // 4. VERIFY
        Assertions.assertNotNull(result);
        Assertions.assertEquals(10L, result.getId());
        Assertions.assertEquals("IT Services", result.getName());
        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void testUpdateDepartment() {
        // 1. SETUP
        Long deptId = 5L;
        Department existingDept = new Department();
        existingDept.setId(deptId);
        existingDept.setName("Old Name");

        DepartmentRequestDTO updateDto = new DepartmentRequestDTO();
        updateDto.setName("New Name");

        Department updatedDept = new Department();
        updatedDept.setId(deptId);
        updatedDept.setName("New Name");

        DepartmentDTO resultDto = new DepartmentDTO();
        resultDto.setId(deptId);
        resultDto.setName("New Name");

        // 2. MOCK
        when(departmentRepository.findById(deptId)).thenReturn(Optional.of(existingDept));
        when(departmentRepository.save(existingDept)).thenReturn(updatedDept);
        when(departmentMapper.toDto(updatedDept)).thenReturn(resultDto);

        // 3. EXECUTE
        DepartmentDTO result = departmentService.updateDepartment(deptId, updateDto);

        // 4. VERIFY
        Assertions.assertEquals("New Name", result.getName());
        Assertions.assertEquals("New Name", existingDept.getName());
    }
}