package com.example.finalrpo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_categories")
@Getter
@Setter
public class Category extends BaseEntity {
    private String name;
}
