package com.example.finalrpo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "t_roles")
@Getter
@Setter
public class Role extends BaseEntity implements GrantedAuthority {
    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }
}
