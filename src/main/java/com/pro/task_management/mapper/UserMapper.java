package com.pro.task_management.mapper;

import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.request.UserUpdateRequestDTO;
import com.pro.task_management.dto.response.UserResponseDTO;
import com.pro.task_management.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponseDTO toDTO(User user);

    List<UserResponseDTO> toDTOList(List<User> users);

    @Mapping(target = "createdTasks", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "projectMemberships", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdTasks", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "projectMemberships", ignore = true)
    void updateEntityFromDTO(UserRequestDTO dto, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdTasks", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "projectMemberships", ignore = true)
    void updateUserFromDto(UserUpdateRequestDTO dto, @MappingTarget User user);

}
