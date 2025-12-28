package com.example.finalrpo.mapper;

import com.example.finalrpo.dto.DepartmentDTO;
import com.example.finalrpo.dto.DepartmentRequestDTO;
import com.example.finalrpo.models.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDTO toDto(Department department);
    List<DepartmentDTO> toDtoList(List<Department> departments);

    @Mapping(target = "id", ignore = true)
    Department toEntity(DepartmentRequestDTO request);
}
