package com.example.finalrpo.service;

import com.example.finalrpo.dto.CategoryDTO;
import com.example.finalrpo.mapper.CategoryMapper;
import com.example.finalrpo.models.Category;
import com.example.finalrpo.repository.CategoryRepository;
import com.example.finalrpo.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock private CategoryRepository categoryRepository;
    @Mock private CategoryMapper categoryMapper;

    @InjectMocks private CategoryServiceImpl categoryService;

    @Test
    void testGetAllCategories() {
        // 1. SETUP
        Category c1 = new Category(); c1.setName("Hardware");
        Category c2 = new Category(); c2.setName("Software");
        List<Category> list = List.of(c1, c2);

        CategoryDTO d1 = new CategoryDTO(); d1.setName("Hardware");
        CategoryDTO d2 = new CategoryDTO(); d2.setName("Software");

        // 2. MOCK
        when(categoryRepository.findAll()).thenReturn(list);
        when(categoryMapper.toDtoList(list)).thenReturn(List.of(d1, d2));

        // 3. EXECUTE
        List<CategoryDTO> result = categoryService.getAllCategories();

        // 4. VERIFY
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Hardware", result.get(0).getName());
    }
}