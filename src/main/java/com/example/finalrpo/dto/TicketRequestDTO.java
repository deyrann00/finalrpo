package com.example.finalrpo.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TicketRequestDTO {
    private String title;
    private String description;
    private Long categoryId;
    private List<Long> tagIds;
}
