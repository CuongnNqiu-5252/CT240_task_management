package com.pro.task_management.repository;

import com.pro.task_management.entity.ProjectMember;
import com.pro.task_management.entity.ProjectMemberId;
import com.pro.task_management.enums.ProjectRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    Page<ProjectMember> findByProjectId(String projectId, Pageable pageable);

    Page<ProjectMember> findByUserId(String userId, Pageable pageable);

    void deleteByProjectId(String projectId);

    void deleteByUserId(String userId);

    boolean existsByProject_IdAndUser_IdAndRole(
            String projectId,
            String userId,
            ProjectRole role
    );

    boolean existsByUserIdAndProjectId(String userId, String projectId);

    Optional<ProjectMember> findByProject_IdAndRole(String projectId, ProjectRole role);

}
