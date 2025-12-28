package com.example.finalrpo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequestDTO {
    private String commentText;
    private Long ticketId;
}
