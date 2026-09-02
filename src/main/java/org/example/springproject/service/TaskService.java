package org.example.springproject.service;

import org.example.springproject.Enum.Priority;
import org.example.springproject.Enum.Status;
import org.example.springproject.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class TaskService {

    private final Map<Long, Task> taskMap = Map.of(
            1L, new Task(
                    1L,
                    2L,
                    3L,
                    Status.CREATED,
                    LocalDateTime.now(),
                    LocalDate.now().plusDays(5),
                    Priority.Medium
            ),
                    2L, new Task(
                    2L,
                    24L,
                    34L,
                    Status.CREATED,
                    LocalDateTime.now(),
                    LocalDate.now().plusDays(5),
                    Priority.Medium
            ),
                    3L, new Task(
                    3L,
                    23L,
                    33L,
                    Status.CREATED,
                    LocalDateTime.now(),
                    LocalDate.now().plusDays(5),
                    Priority.Medium
            )
    );


    public Task getTaskSevice(Long id) {
        if (!taskMap.containsKey(id)) {
            throw new NoSuchElementException("Not found by id = " + id);
        }
        return taskMap.get(id);
    }

    public List<Task> getAllTasks() {
        return taskMap.values().stream().toList();
    }
}
