package com.example.finalrpo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {
    private String email;
    private String password;
    private String fullName;
    private Long departmentId;
}