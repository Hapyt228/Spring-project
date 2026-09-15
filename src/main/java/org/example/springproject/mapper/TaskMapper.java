package org.example.springproject.mapper;

import org.example.springproject.object.Task;
import org.example.springproject.object.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task taskEntityByTask(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getCreatorId(),
                taskEntity.getAssignedUserId(),
                taskEntity.getStatus(),
                taskEntity.getCreateDateTime(),
                taskEntity.getDeadlineDate(),
                taskEntity.getPriority(),
                taskEntity.getDoneDateTime()
        );
    }

    public TaskEntity taskByTaskEntity(Task task) {
        return new TaskEntity(
                null,
                task.creatorId(),
                task.assignedUserId(),
                task.status(),
                task.createDateTime(),
                task.deadlineDate(),
                task.priority(),
                task.doneDateTime()
        );
    }
}
