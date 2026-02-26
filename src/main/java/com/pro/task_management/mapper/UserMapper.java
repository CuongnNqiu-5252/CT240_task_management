package com.pro.task_management.mapper;

import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.request.UserUpdateRequestDTO;
import com.pro.task_management.dto.response.UserResponseDTO;
import com.pro.task_management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponseDTO toDTO(User user);

    List<UserResponseDTO> toDTOList(List<User> users);

    @Mapping(target = "createdTasks", ignore = true)
    @Mapping(target = "assignedTasks", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "projectMemberships", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdTasks", ignore = true)
    @Mapping(target = "assignedTasks", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "projectMemberships", ignore = true)
    void updateEntityFromDTO(UserRequestDTO dto, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdTasks", ignore = true)
    @Mapping(target = "assignedTasks", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "projectMemberships", ignore = true)
    void toUpdateDTO(UserUpdateRequestDTO dto, @MappingTarget User user);
}
