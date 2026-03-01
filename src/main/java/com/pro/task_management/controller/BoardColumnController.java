package com.pro.task_management.controller;

import com.pro.task_management.dto.request.BoardColumnRequestDTO;
import com.pro.task_management.dto.request.BoardColumnUpdateDTO;
import com.pro.task_management.dto.response.BoardColumnResponseDTO;
import com.pro.task_management.service.BoardColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board-columns")
@RequiredArgsConstructor
public class BoardColumnController {

    private final BoardColumnService boardColumnService;

    @PostMapping
    public ResponseEntity<BoardColumnResponseDTO> createBoardColumn(@Valid @RequestBody BoardColumnRequestDTO requestDTO) {
        BoardColumnResponseDTO response = boardColumnService.createBoardColumn(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardColumnResponseDTO> getBoardColumnById(@PathVariable String id) {
        BoardColumnResponseDTO response = boardColumnService.getBoardColumnById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<BoardColumnResponseDTO>> getBoardColumnsByProject(@PathVariable String projectId) {
        List<BoardColumnResponseDTO> response = boardColumnService.getBoardColumnsByProject(projectId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardColumnResponseDTO> updateBoardColumn(
            @PathVariable String id,
            @Valid @RequestBody BoardColumnUpdateDTO requestDTO) {
        BoardColumnResponseDTO response = boardColumnService.updateBoardColumn(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardColumn(@PathVariable String id) {
        boardColumnService.deleteBoardColumn(id);
        return ResponseEntity.noContent().build();
    }
}
