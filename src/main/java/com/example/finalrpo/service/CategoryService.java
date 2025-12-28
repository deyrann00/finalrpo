package com.example.finalrpo.service;

import com.example.finalrpo.dto.CategoryDTO;
import com.example.finalrpo.dto.CategoryRequestDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategory(Long id);
    CategoryDTO createCategory(CategoryRequestDTO dto);
    CategoryDTO updateCategory(Long id, CategoryRequestDTO dto);
    void deleteCategory(Long id);
}
