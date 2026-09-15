    package org.example.springproject.service;

    import org.example.springproject.filter.TaskSearchFilter;
    import org.example.springproject.mapper.TaskMapper;
    import org.example.springproject.object.Task;
    import org.example.springproject.enum_model.Status;
    import org.example.springproject.object.TaskEntity;
    import org.example.springproject.repository.TaskRepository;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.NoSuchElementException;


    @Service
    public class TaskService {

        private final TaskRepository taskRepository;
        private final TaskMapper taskMapper;

        public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
            this.taskRepository = taskRepository;
            this.taskMapper = taskMapper;
        }

        public Task getTaskService(Long id) {

            TaskEntity taskByThisId =  taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found by id = " + id));
            return  taskMapper.taskEntityByTask(taskByThisId);
        }

        public List<Task> getAllTasks() {
            List<TaskEntity> allTask = taskRepository.findAll();
            return allTask.stream()
                    .map(taskMapper::taskEntityByTask)
                    .toList();
        }

        public  Task createTask(Task task) {
            if (task.status() != null) {
                throw new IllegalArgumentException("Task status is already set");
            }
            var taskCreated = taskMapper.taskByTaskEntity(task);
            taskCreated.setStatus(Status.CREATED);
            var saveEntity = taskRepository.save(taskCreated);
            return taskMapper.taskEntityByTask(saveEntity);
        }

        public Task updateTask(Task task, Long id) {
            var taskByUpdate = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found by id = " + id));
            if (taskByUpdate.getStatus() == Status.DONE && task.status() != Status.IN_PROGRESS) {
                throw new IllegalArgumentException("Task status done");
            }
            taskByUpdate.setAssignedUserId(task.assignedUserId());
            taskByUpdate.setCreatorId(task.creatorId());
            taskByUpdate.setStatus(task.status());
            taskByUpdate.setDeadlineDate(task.deadlineDate());
            taskByUpdate.setPriority(task.priority());
            var saveEntity = taskRepository.save(taskByUpdate);
            return  taskMapper.taskEntityByTask(saveEntity);
        }

        public void deleteTask(Long id) {
            TaskEntity taskByThisId =  taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found by id = " + id));
            taskByThisId.setStatus(Status.DONE);
            taskRepository.save(taskByThisId);
        }

        public Task startTask(Long id) {
            TaskEntity taskByStart =  taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found by id = " + id));

            if (taskByStart.getStatus() != Status.CREATED) {
                throw new IllegalArgumentException("Task can be started only from CREATED status");
            }
            long activeTasksCount = taskRepository.countByAssignedUserIdAndStatus(
                    taskByStart.getAssignedUserId(),
                    Status.IN_PROGRESS
            );
            if (activeTasksCount >= 5) {
               throw new IllegalArgumentException("Active tasks count is greater than 5");
            }
            taskByStart.setStatus(Status.IN_PROGRESS);
            var saveEntity = taskRepository.save(taskByStart);
            return  taskMapper.taskEntityByTask(saveEntity);
        }

        public Task completeTask(Long id) {
            TaskEntity taskByComplete =  taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found by id = " + id));

            if (taskByComplete.getStatus() != Status.IN_PROGRESS) {
                throw new IllegalArgumentException("Task can be progress only from DONE status");
            }
            taskByComplete.setStatus(Status.DONE);
            taskByComplete.setDoneDateTime(LocalDateTime.now());
            var saveEntity = taskRepository.save(taskByComplete);
            return  taskMapper.taskEntityByTask(saveEntity);
        }

        public List<Task> searchTasks(TaskSearchFilter filter, Pageable pageable) {
            List<TaskEntity> foundTasks = taskRepository.searchTasks(
                    filter.creatorId(),
                    filter.assignedUserId(),
                    filter.status(),
                    filter.priority(),
                    pageable
            );
            return foundTasks.stream()
                    .map(taskMapper::taskEntityByTask)
                    .toList();

        }
    }
