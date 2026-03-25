package com.pro.task_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaskAssigneesId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "task_id")
    private String taskId;
}
