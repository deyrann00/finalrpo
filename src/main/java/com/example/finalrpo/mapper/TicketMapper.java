package com.example.finalrpo.mapper;

import com.example.finalrpo.dto.TicketDTO;
import com.example.finalrpo.dto.TicketRequestDTO;
import com.example.finalrpo.models.Ticket;
import com.example.finalrpo.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "author.fullName", target = "authorName")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "mapTagsToStrings")
    TicketDTO toDto(Ticket ticket);

    List<TicketDTO> toDtoList(List<Ticket> tickets);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "category", ignore = true)
    Ticket toEntity(TicketRequestDTO request);

    @Named("mapTagsToStrings")
    default List<String> mapTagsToStrings(List<Tag> tags) {
        if (tags == null) return null;
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}