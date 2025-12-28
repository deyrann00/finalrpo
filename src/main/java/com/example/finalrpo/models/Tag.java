package com.example.finalrpo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_tags")
@Getter
@Setter
public class Tag extends BaseEntity {
    private String name;
}
