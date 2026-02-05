package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.BoardColumnRequestDTO;
import com.pro.task_management.dto.response.BoardColumnResponseDTO;
import com.pro.task_management.entity.BoardColumn;
import com.pro.task_management.entity.Project;
import com.pro.task_management.exception.ResourceNotFoundException;
import com.pro.task_management.mapper.BoardColumnMapper;
import com.pro.task_management.repository.BoardColumnRepository;
import com.pro.task_management.repository.ProjectRepository;
import com.pro.task_management.service.BoardColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardColumnServiceImpl implements BoardColumnService {

    private final BoardColumnRepository boardColumnRepository;
    private final ProjectRepository projectRepository;
    private final BoardColumnMapper boardColumnMapper;

    @Override
    public BoardColumnResponseDTO createBoardColumn(BoardColumnRequestDTO requestDTO) {
        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", requestDTO.getProjectId()));

        BoardColumn boardColumn = BoardColumn.builder()
                .name(requestDTO.getName())
                .project(project)
                .build();

        BoardColumn saved = boardColumnRepository.save(boardColumn);
        return boardColumnMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardColumnResponseDTO getBoardColumnById(String id) {
        BoardColumn boardColumn = boardColumnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BoardColumn", "id", id));
        return boardColumnMapper.toDTO(boardColumn);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardColumnResponseDTO> getBoardColumnsByProject(String projectId) {
        List<BoardColumn> boardColumns = boardColumnRepository.findByProjectId(projectId);
        return boardColumnMapper.toDTOList(boardColumns);
    }

    @Override
    public BoardColumnResponseDTO updateBoardColumn(String id, BoardColumnRequestDTO requestDTO) {
        BoardColumn boardColumn = boardColumnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BoardColumn", "id", id));
        boardColumn.setName(requestDTO.getName());
        BoardColumn updated = boardColumnRepository.save(boardColumn);
        return boardColumnMapper.toDTO(updated);
    }

    @Override
    public void deleteBoardColumn(String id) {
        BoardColumn boardColumn = boardColumnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BoardColumn", "id", id));
        boardColumnRepository.delete(boardColumn);
    }
}
