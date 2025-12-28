package com.example.finalrpo.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private String departmentName;
    private List<String> roles;
}