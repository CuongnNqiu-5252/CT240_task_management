package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.BoardColumnResponseDTO;
import com.pro.task_management.entity.BoardColumn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { TaskMapper.class })
public interface BoardColumnMapper {

    @Mapping(source = "tasks", target = "tasks")
    @Mapping(source = "taskOrderIds", target = "taskOrderIds")
    BoardColumnResponseDTO toDTO(BoardColumn boardColumn);

    List<BoardColumnResponseDTO> toDTOList(List<BoardColumn> boardColumns);
}
