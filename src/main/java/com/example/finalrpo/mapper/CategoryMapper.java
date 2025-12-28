package com.example.finalrpo.mapper;

import com.example.finalrpo.dto.CategoryDTO;
import com.example.finalrpo.dto.CategoryRequestDTO;
import com.example.finalrpo.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    List<CategoryDTO> toDtoList(List<Category> categories);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequestDTO request);
}
