package com.example.finalrpo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_comments")
@Getter
@Setter
public class Comment extends BaseEntity {
    private String commentText;
    private LocalDateTime postDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
