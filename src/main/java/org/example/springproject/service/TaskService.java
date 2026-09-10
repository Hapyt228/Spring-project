    package org.example.springproject.service;

    import org.example.springproject.object.Task;
    import org.example.springproject.enum_model.Status;
    import org.example.springproject.object.TaskEntity;
    import org.example.springproject.repository.TaskRepository;
    import org.springframework.stereotype.Service;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.NoSuchElementException;


    @Service
    public class TaskService {

        private Map<Long, Task> taskMap = new HashMap<Long, Task>();
        private final TaskRepository taskRepository;

        public TaskService(TaskRepository taskRepository) {
            this.taskRepository = taskRepository;
        }

        public Task getTaskService(Long id) {

            TaskEntity taskByThisId =  taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found by id = " + id));
            return  taskEntityByTask(taskByThisId);
        }

        public List<Task> getAllTasks() {
            List<TaskEntity> allTask = taskRepository.findAll();
            return allTask.stream()
                    .map(this::taskEntityByTask)
                    .toList();
        }

        public  Task createTask(Task task) {
            if (task.id() != null) {
                throw new IllegalArgumentException("Task id is already set");
            }

            if (task.status() != null) {
                throw new IllegalArgumentException("Task status is already set");
            }
            var taskCreated = taskByTaskEntity(task);
            taskCreated.setStatus(Status.CREATED);
            var saveEntity = taskRepository.save(taskCreated);
            return taskEntityByTask(saveEntity);
        }

        public Task updateTask(Task task, Long id) {
            var taskByUpdate = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found by id = " + id));
            if (taskByUpdate.getStatus() == Status.DONE){
                throw new IllegalArgumentException("Task status done");
            }
            taskByUpdate.setAssignedUserId(task.assignedUserId());
            taskByUpdate.setCreatorId(task.creatorId());
            taskByUpdate.setStatus(task.status());
            taskByUpdate.setDeadlineDate(task.deadlineDate());
            taskByUpdate.setPriority(task.priority());
            var saveEntity = taskRepository.save(taskByUpdate);
            return  taskEntityByTask(saveEntity);
        }

        public void deleteTask(Long id) {
            TaskEntity taskByThisId =  taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found by id = " + id));
            taskByThisId.setStatus(Status.DONE);
            taskRepository.save(taskByThisId);
        }

        public Task startTask(Task task) {
            if (task.id() != null) {
                throw new IllegalArgumentException("Task id is already set");
            }
            if (task.assignedUserId() == null) {
                throw new IllegalArgumentException("Task assignedUserId not set");
            }
            var taskByStart  = taskByTaskEntity(task);
            long activeTasksCount = taskRepository.countByAssignedUserIdAndStatus(
                    taskByStart.getAssignedUserId(),
                    Status.IN_PROGRESS
            );
            if (activeTasksCount >= 5) {
               throw new IllegalArgumentException("Active tasks count is greater than 5");
            }
            taskByStart.setStatus(Status.IN_PROGRESS);
            var saveEntity = taskRepository.save(taskByStart);
            return  taskEntityByTask(saveEntity);
        }

        private Task taskEntityByTask(TaskEntity taskEntity) {
            return new Task(
                    taskEntity.getId(),
                    taskEntity.getCreatorId(),
                    taskEntity.getAssignedUserId(),
                    taskEntity.getStatus(),
                    taskEntity.getCreateDateTime(),
                    taskEntity.getDeadlineDate(),
                    taskEntity.getPriority()
                    );
        }

        private TaskEntity taskByTaskEntity(Task task) {
            return new TaskEntity(
                    null,
                    task.creatorId(),
                    task.assignedUserId(),
                    task.status(),
                    task.createDateTime(),
                    task.deadlineDate(),
                    task.priority()
            );
        }


    }
