package com.example.finalrpo.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class CommentDTO {
    private Long id;
    private String commentText;
    private LocalDateTime postDate;
    private String authorName;
    private Long ticketId;
}
