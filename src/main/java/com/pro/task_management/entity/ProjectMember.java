package com.pro.task_management.entity;

import com.pro.task_management.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nguoidung_duan")
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

    public ProjectMemberId getId() {
        return id;
    }

    public void setId(ProjectMemberId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

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
