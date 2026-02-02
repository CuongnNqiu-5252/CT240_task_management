package com.pro.task_management.repository;

import com.pro.task_management.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, String> {

    List<BoardColumn> findByProjectId(String projectId);

    void deleteByProjectId(String projectId);
}
