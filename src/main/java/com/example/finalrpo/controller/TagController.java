package com.example.finalrpo.controller;

import com.example.finalrpo.dto.TagDTO;
import com.example.finalrpo.dto.TagRequestDTO;
import com.example.finalrpo.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 1. READ ALL
    @GetMapping
    public List<TagDTO> getAll() {
        return tagService.getAllTags();
    }

    // 2. READ ONE
    @GetMapping("/{id}")
    public TagDTO getById(@PathVariable Long id) {
        return tagService.getTag(id);
    }

    // 3. CREATE
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TagDTO create(@RequestBody TagRequestDTO request) {
        return tagService.createTag(request);
    }

    // 4. UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TagDTO update(@PathVariable Long id, @RequestBody TagRequestDTO dto) {
        return tagService.updateTag(id, dto);
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}