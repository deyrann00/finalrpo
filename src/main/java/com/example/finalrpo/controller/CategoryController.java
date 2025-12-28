package com.example.finalrpo.controller;

import com.example.finalrpo.dto.CategoryDTO;
import com.example.finalrpo.dto.CategoryRequestDTO;
import com.example.finalrpo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 1. READ ALL
    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAllCategories();
    }

    // 2. READ ONE (New)
    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    // 3. CREATE
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryDTO create(@RequestBody CategoryRequestDTO request) {
        return categoryService.createCategory(request);
    }

    // 4. UPDATE (New)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryRequestDTO dto) {
        return categoryService.updateCategory(id, dto);
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}