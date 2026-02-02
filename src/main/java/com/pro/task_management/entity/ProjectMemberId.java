package com.pro.task_management.entity;

import com.pro.task_management.enums.ProjectRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProjectMemberId implements Serializable {

    @Column(name = "dua_id")
    private String userId;

    @Column(name = "con_id")
    private String projectId;
}
