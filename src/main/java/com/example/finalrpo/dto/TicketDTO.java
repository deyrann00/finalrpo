package com.example.finalrpo.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TicketDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String authorName;
    private String categoryName;
    private List<String> tags;
}
