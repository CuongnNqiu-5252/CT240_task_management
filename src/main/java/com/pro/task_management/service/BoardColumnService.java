package com.pro.task_management.service;

import com.pro.task_management.dto.request.BoardColumnRequestDTO;
import com.pro.task_management.dto.response.BoardColumnResponseDTO;

import java.util.List;

public interface BoardColumnService {

    BoardColumnResponseDTO createBoardColumn(BoardColumnRequestDTO requestDTO);

    BoardColumnResponseDTO getBoardColumnById(String id);

    List<BoardColumnResponseDTO> getBoardColumnsByProject(String projectId);

    BoardColumnResponseDTO updateBoardColumn(String id, BoardColumnRequestDTO requestDTO);

    void deleteBoardColumn(String id);
}
