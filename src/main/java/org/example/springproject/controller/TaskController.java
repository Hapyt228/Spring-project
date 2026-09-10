package org.example.springproject.controller;

import org.example.springproject.object.Task;
import org.example.springproject.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService ;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskService(
          @PathVariable("id") Long id
    ) {
        try {
            log.info("getTaskService: id= " + id);
            return ResponseEntity.ok(taskService.getTaskService(id));
        } catch (Exception e) {
            log.info("Exception not found by getTaskService");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        log.info("getAllTasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(
            @RequestBody Task task
    ) {
        log.info("createTask: task= " + task);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(task));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") Long id,
            @RequestBody Task task
    ){
        try{
        log.info("updateTask: task= " + task);
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.updateTask(task, id));
        } catch (Exception e) {
            log.info("Exception not found by updateTask");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("id") Long id
    ){
        try{
        log.info("deleteTask: task= " + id);
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
        } catch (Exception e) {
            log.info("Exception not found by deleteTask");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/tasks/{id}/start")
    public ResponseEntity<Task> startTask(
            @RequestBody Task task
    ) {
        try {
            log.info("startTask: task= " + task);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(taskService.startTask(task));
        } catch (Exception e) {
            log.info("БЕБЕ");
            return ResponseEntity.notFound().build();
        }
    }

}
