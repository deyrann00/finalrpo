package com.example.finalrpo.mapper;

import com.example.finalrpo.dto.CommentDTO;
import com.example.finalrpo.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.fullName", target = "authorName")
    @Mapping(source = "ticket.id", target = "ticketId")
    CommentDTO toDto(Comment comment);

    List<CommentDTO> toDtoList(List<Comment> comments);
}