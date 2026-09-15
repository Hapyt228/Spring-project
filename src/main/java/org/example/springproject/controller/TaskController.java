package org.example.springproject.controller;

import jakarta.validation.Valid;
import org.example.springproject.enum_model.Priority;
import org.example.springproject.enum_model.Status;
import org.example.springproject.filter.TaskSearchFilter;
import org.example.springproject.object.Task;
import org.example.springproject.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService ;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskService(
          @PathVariable("id") Long id
    ) {
            log.info("getTaskService: id= " + id);
            return ResponseEntity.ok(taskService.getTaskService(id));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        log.info("getAllTasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(
            @RequestBody @Valid Task task
    ) {
        log.info("createTask: task= " + task);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(task));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") Long id,
            @RequestBody @Valid Task task
    ){
        log.info("updateTask: task= " + task);
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.updateTask(task, id));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("id") Long id
    ){
        log.info("deleteTask: task= " + id);
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/tasks/{id}/start")
    public ResponseEntity<Task> startTask(
            @PathVariable("id") Long id
    ) {
            log.info("startTask: id= " + id);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(taskService.startTask(id));
    }

    @PostMapping("/tasks/{id}/complete")
    public ResponseEntity<Task> completeTask(
            @PathVariable("id") Long id
    ){
        log.info("completeTask: id= " + id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(taskService.completeTask(id));
    }

    @GetMapping("/tasks/search")
    public ResponseEntity<List<Task>> searchTasks(
            @RequestParam(required = false) Long creatorId,
            @RequestParam(required = false) Long assignedUserId,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        TaskSearchFilter filter = new TaskSearchFilter(creatorId, assignedUserId, status, priority);
        Pageable pageable = PageRequest.of(page, size);
        log.info("searchTasks: filter= " + filter + ", page= " + page + ", size= " + size);
        return ResponseEntity.ok(taskService.searchTasks(filter, pageable));
    }


}
