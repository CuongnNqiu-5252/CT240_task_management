package com.pro.task_management.repository;

import com.pro.task_management.entity.ProjectMember;
import com.pro.task_management.entity.ProjectMemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByProjectId(String projectId);

    Page<ProjectMember> findByUserId(String userId, Pageable pageable);

    void deleteByProjectId(String projectId);

    void deleteByUserId(String userId);
}
