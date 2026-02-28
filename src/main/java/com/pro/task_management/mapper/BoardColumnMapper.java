package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.BoardColumnResponseDTO;
import com.pro.task_management.entity.BoardColumn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardColumnMapper {

    BoardColumnResponseDTO toDTO(BoardColumn boardColumn);

    List<BoardColumnResponseDTO> toDTOList(List<BoardColumn> boardColumns);
}
