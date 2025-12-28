package com.example.finalrpo.service.impl;

import com.example.finalrpo.dto.CategoryDTO;
import com.example.finalrpo.dto.CategoryRequestDTO;
import com.example.finalrpo.mapper.CategoryMapper;
import com.example.finalrpo.models.Category;
import com.example.finalrpo.repository.CategoryRepository;
import com.example.finalrpo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryRequestDTO dto) {
        // Mapper converts RequestDTO -> Entity
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(dto)));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(dto.getName());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
