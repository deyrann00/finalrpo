package com.example.finalrpo.service;

import com.example.finalrpo.dto.TagDTO;
import com.example.finalrpo.dto.TagRequestDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> getAllTags();
    TagDTO getTag(Long id);
    TagDTO createTag(TagRequestDTO dto);
    TagDTO updateTag(Long id, TagRequestDTO dto);
    void deleteTag(Long id);
}
