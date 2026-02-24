package com.pro.task_management.entity;

import com.pro.task_management.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMember {

    @EmbeddedId
    private ProjectMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "dua_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "con_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private ProjectRole role = ProjectRole.MEMBER;

    // Helper method to create ProjectMember
    public static ProjectMember create(User user, Project project, ProjectRole role) {
        ProjectMember member = new ProjectMember();
        member.setId(new ProjectMemberId(user.getId(), project.getId()));
        member.setUser(user);
        member.setProject(project);
        member.setRole(role);
        return member;
    }
}
