package com.example.finalrpo.mapper;

import com.example.finalrpo.dto.TagDTO;
import com.example.finalrpo.dto.TagRequestDTO;
import com.example.finalrpo.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDto(Tag tag);
    List<TagDTO> toDtoList(List<Tag> tags);

    @Mapping(target = "id", ignore = true)
    Tag toEntity(TagRequestDTO request);
}
