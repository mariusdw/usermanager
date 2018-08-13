package com.github.mariusdw.usermanager.web.handler;

import com.github.mariusdw.usermanager.web.request.CreateTaskRequest;
import com.github.mariusdw.usermanager.web.request.UpdateTaskRequest;
import com.github.mariusdw.usermanager.web.response.*;
import com.github.mariusdw.usermanager.datastore.entity.TaskEntity;
import com.github.mariusdw.usermanager.datastore.entity.UserEntity;
import com.github.mariusdw.usermanager.datastore.repository.TaskRepository;
import com.github.mariusdw.usermanager.datastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultTaskHandler implements TaskHandler {
    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Autowired
    public DefaultTaskHandler(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse processCreateTaskRequest(Long userId, CreateTaskRequest createTaskRequest) {
        TaskResponse taskResponse;

        String taskName = createTaskRequest.getName();
        String taskDescription = createTaskRequest.getDescription();
        Date dateTime = createTaskRequest.getDateTime();
        if (taskName != null && taskDescription != null && dateTime != null) {
            Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
            if (userEntityOptional.isPresent()) {
                TaskEntity taskEntity = new TaskEntity(userId, taskName, taskDescription, Timestamp.from(dateTime.toInstant()));
                taskRepository.save(taskEntity);
                taskResponse = new TaskResponse(new Task(taskEntity));
            } else {
                taskResponse = new TaskResponse(ResponseCode.USER_DOES_NOT_EXIST);
            }
        } else {
            taskResponse = new TaskResponse(ResponseCode.REQUEST_FORMAT_ERROR);
        }

        return taskResponse;
    }

    @Override
    public TaskResponse processUpdateTaskRequest(Long userId,
                                                 Long taskId,
                                                 UpdateTaskRequest updateTaskRequest) {
        TaskResponse taskResponse;

        Optional<TaskEntity> taskEntityOptional = taskRepository.findById(taskId);
        if (taskEntityOptional.isPresent()) {
            TaskEntity taskEntity = taskEntityOptional.get();
            if (taskEntity.getUserId().equals(userId)) {
                boolean updated = false;
                if (updateTaskRequest.getName() != null) {
                    taskEntity.setName(updateTaskRequest.getName());
                    updated = true;
                }
                if (updateTaskRequest.getDescription() != null) {
                    taskEntity.setDescription(updateTaskRequest.getDescription());
                    updated = true;
                }
                if (updateTaskRequest.getDateTime() != null) {
                    taskEntity.setDateTime(Timestamp.from(updateTaskRequest.getDateTime().toInstant()));
                    updated = true;
                }
                if (updated) {
                    taskRepository.save(taskEntity);
                }
                taskResponse = new TaskResponse(new Task(taskEntity));
            } else {
                taskResponse = new TaskResponse(ResponseCode.TASK_NOT_LINKED_TO_USER);
            }
        } else {
            taskResponse = new TaskResponse(ResponseCode.TASK_DOES_NOT_EXIST);
        }

        return taskResponse;
    }

    @Override
    public BasicResponse processDeleteTaskRequest(Long userId, Long taskId) {
        BasicResponse basicResponse;

        Optional<TaskEntity> taskEntityOptional = taskRepository.findById(taskId);
        if (taskEntityOptional.isPresent()) {
            TaskEntity taskEntity = taskEntityOptional.get();
            if (taskEntity.getUserId().equals(userId)) {
                taskRepository.delete(taskEntity);
                basicResponse = new BasicResponse(ResponseCode.SUCCESS);
            } else {
                basicResponse = new BasicResponse(ResponseCode.TASK_NOT_LINKED_TO_USER);
            }
        } else {
            basicResponse = new BasicResponse(ResponseCode.TASK_DOES_NOT_EXIST);
        }

        return basicResponse;
    }

    @Override
    public TaskResponse processGetTaskRequest(Long userId, Long taskId) {
        TaskResponse taskResponse;

        Optional<TaskEntity> taskEntityOptional = taskRepository.findById(taskId);
        if (taskEntityOptional.isPresent()) {
            TaskEntity taskEntity = taskEntityOptional.get();
            if (taskEntity.getUserId().equals(userId)) {
                taskResponse = new TaskResponse(new Task(taskEntity));
            } else {
                taskResponse = new TaskResponse(ResponseCode.TASK_NOT_LINKED_TO_USER);
            }
        } else {
            taskResponse = new TaskResponse(ResponseCode.TASK_DOES_NOT_EXIST);
        }

        return taskResponse;
    }

    @Override
    public TasksResponse processGetAllTasksForUserRequest(Long userId) {
        TasksResponse tasksResponse;

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            Iterable<TaskEntity> taskEntities = taskRepository.findByUserId(userId);
            List<Task> tasks = new LinkedList<>();
            if (taskEntities != null) {
                for (TaskEntity taskEntity : taskEntities) {
                    tasks.add(new Task(taskEntity));
                }
            }
            tasksResponse = new TasksResponse(tasks);
        } else {
            tasksResponse = new TasksResponse(ResponseCode.USER_DOES_NOT_EXIST);
        }

        return tasksResponse;
    }
}
