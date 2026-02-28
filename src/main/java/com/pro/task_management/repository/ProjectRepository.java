package com.pro.task_management.repository;

import com.pro.task_management.entity.Project;
import com.pro.task_management.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByNameContainingIgnoreCase(String name);

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.projectMembers pm LEFT JOIN FETCH pm.user")
    Page<Project> findAllWithMembers(Pageable pageable);
}
