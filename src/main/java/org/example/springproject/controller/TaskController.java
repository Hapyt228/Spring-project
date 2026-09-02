package org.example.springproject.controller;

import org.example.springproject.Task;
import org.example.springproject.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public Task getTaskService(
          @PathVariable("id") Long id
    ) {
        log.info("getTaskService: id= " + id);
        return taskService.getTaskSevice(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        log.info("getAllTasks");
        return taskService.getAllTasks();
    }
}
