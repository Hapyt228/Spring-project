package org.example.springproject.object;

import jakarta.persistence.*;
import org.example.springproject.enum_model.Priority;
import org.example.springproject.enum_model.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creatorId")
    private Long creatorId;

    @Column(name = "assignedUserId")
    private Long assignedUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "createDateTime")
    private LocalDateTime createDateTime;

    @Column(name = "deadlineDate")
    private LocalDate deadlineDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    public TaskEntity(Long id, Long creatorId, Long assignedUserId, Status status, LocalDateTime createDateTime, LocalDate deadlineDate, Priority priority) {
        this.id = id;
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createDateTime = createDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
    }

    public TaskEntity() {
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
