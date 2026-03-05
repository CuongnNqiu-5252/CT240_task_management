package com.pro.task_management.entity;

import com.pro.task_management.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_assignees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskAssignees {
    @EmbeddedId
    private TaskAssigneesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("taskId")
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // Helper method to create ProjectMember
    public static TaskAssignees create(User user, Task task) {
        TaskAssignees taskAssignees = new TaskAssignees();
        taskAssignees.setId(new TaskAssigneesId(user.getId(), task.getId()));
        taskAssignees.setUser(user);
        taskAssignees.setTask(task);
        return taskAssignees;
    }
}
