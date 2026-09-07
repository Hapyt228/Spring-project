    package org.example.springproject.service;

    import org.example.springproject.Task;
    import org.example.springproject.enum_model.Status;
    import org.springframework.stereotype.Service;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.NoSuchElementException;
    import java.util.concurrent.atomic.AtomicLong;

    @Service
    public class TaskService {

        private Map<Long, Task> taskMap = new HashMap<Long, Task>();
        private AtomicLong idGenerator = new AtomicLong(0);

        public Task getTaskService(Long id) {
            if (!taskMap.containsKey(id)) {
                throw new NoSuchElementException("Not found by id = " + id);
            }
            return taskMap.get(id);
        }

        public List<Task> getAllTasks() {
            return taskMap.values().stream().toList();
        }

        public  Task createTask(Task task) {
            if (task.id() != null) {
                throw new IllegalArgumentException("Task id is already set");
            }
            if (task.status() != null) {
                throw new IllegalArgumentException("Task status is already set");
            }
            var taskCreated = new Task(
                    idGenerator.incrementAndGet(),
                    task.creatorId(),
                    task.assignedUserId(),
                    Status.CREATED,
                    task.createDateTime(),
                    task.deadlineDate(),
                    task.priority()
            );
            taskMap.put(taskCreated.id() ,taskCreated);
            return taskCreated;
        }

        public Task updateTask(Task task, Long id) {
            if (taskMap.get(id) != null) {
                throw new IllegalArgumentException("Task id is already set");
            }
            if (!(taskMap.get(id).status() != Status.DONE)){
                throw new IllegalArgumentException("Task status done");
            }
            var taskByUpdate  = new Task(
                    id,
                    task.creatorId(),
                    task.assignedUserId(),
                    Status.IN_PROGRESS,
                    task.createDateTime(),
                    task.deadlineDate(),
                    task.priority()
            );
            taskMap.put(id ,taskByUpdate);
            return  taskByUpdate;
        }

        public void deleteTask(Long id) {
            if (taskMap.get(id) == null) {
                throw new NoSuchElementException("Not found by id = " + id);
            }
            taskMap.remove(id);
        }
    }
