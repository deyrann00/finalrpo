package com.example.finalrpo.mapper;

import com.example.finalrpo.dto.UserDTO;
import com.example.finalrpo.models.User;
import com.example.finalrpo.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToStrings")
    UserDTO toDto(User user);

    List<UserDTO> toDtoList(List<User> users);

    @Named("mapRolesToStrings")
    default List<String> mapRolesToStrings(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getRole)
                .collect(Collectors.toList());
    }
}