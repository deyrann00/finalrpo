package com.example.finalrpo.service.impl;

import com.example.finalrpo.dto.TagDTO;
import com.example.finalrpo.dto.TagRequestDTO;
import com.example.finalrpo.mapper.TagMapper;
import com.example.finalrpo.models.Tag;
import com.example.finalrpo.repository.TagRepository;
import com.example.finalrpo.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagDTO> getAllTags() {
        return tagMapper.toDtoList(tagRepository.findAll());
    }

    @Override
    public TagDTO getTag(Long id) {
        return tagMapper.toDto(tagRepository.findById(id).orElseThrow());
    }

    @Override
    public TagDTO createTag(TagRequestDTO dto) {
        Tag entity = tagMapper.toEntity(dto);
        return tagMapper.toDto(tagRepository.save(entity));
    }

    @Override
    public TagDTO updateTag(Long id, TagRequestDTO dto) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        tag.setName(dto.getName());
        return tagMapper.toDto(tagRepository.save(tag));
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
