package com.example.finalrpo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_departments")
@Getter
@Setter
public class Department extends BaseEntity {
    private String name;
}
