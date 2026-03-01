package com.pro.task_management.mapper;

import com.pro.task_management.dto.request.BoardColumnUpdateDTO;
import com.pro.task_management.dto.response.BoardColumnResponseDTO;
import com.pro.task_management.entity.BoardColumn;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = { TaskMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BoardColumnMapper {

    @Mapping(source = "tasks", target = "tasks")
    @Mapping(source = "taskOrderIds", target = "taskOrderIds")
    BoardColumnResponseDTO toDTO(BoardColumn boardColumn);

    List<BoardColumnResponseDTO> toDTOList(List<BoardColumn> boardColumns);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "project", ignore = true)
    void updateEntityFromDTO(BoardColumnUpdateDTO dto, @MappingTarget BoardColumn boardColumn);
}
