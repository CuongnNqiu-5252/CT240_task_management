package com.pro.task_management.repository;
import com.pro.task_management.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    List<Comment> findByTaskId(String taskId);

    List<Comment> findByUserId(String userId);

    void deleteByTaskId(String taskId);
}
