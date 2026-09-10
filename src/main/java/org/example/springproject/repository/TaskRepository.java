package org.example.springproject.repository;


import org.example.springproject.object.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.springproject.enum_model.Status;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    long countByAssignedUserIdAndStatus(Long assignedUserId, Status status);
}
