package com.github.mariusdw.usermanager.web.controller;

import com.github.mariusdw.usermanager.web.handler.TaskHandler;
import com.github.mariusdw.usermanager.web.request.CreateTaskRequest;
import com.github.mariusdw.usermanager.web.request.UpdateTaskRequest;
import com.github.mariusdw.usermanager.web.response.BasicResponse;
import com.github.mariusdw.usermanager.web.response.TaskResponse;
import com.github.mariusdw.usermanager.web.response.TasksResponse;
import com.github.mariusdw.usermanager.datastore.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
public class TaskController {
    private TaskHandler taskHandler;

    @Autowired
    public TaskController(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    @RequestMapping(method = POST, path = "api/user/{user_id}/task")
    public TaskResponse createTask(@PathVariable("user_id") Long userId,
                                   @RequestBody CreateTaskRequest createTaskRequest) {
        log.debug("Received request: {}", createTaskRequest.toString());

        return taskHandler.processCreateTaskRequest(userId, createTaskRequest);
    }

    @RequestMapping(method = PUT, path = "api/user/{user_id}/task/{task_id}")
    public TaskResponse updateTask(@PathVariable("user_id") Long userId,
                                   @PathVariable("task_id") Long taskId,
                                   @RequestBody UpdateTaskRequest updateTaskRequest) {
        log.debug("Received request: {}", updateTaskRequest.toString());

        return taskHandler.processUpdateTaskRequest(userId, taskId, updateTaskRequest);
    }

    @RequestMapping(method = DELETE, path = "api/user/{user_id}/task/{task_id}")
    public BasicResponse deleteTask(@PathVariable("user_id") Long userId,
                                    @PathVariable("task_id") Long taskId) {
        log.debug("Received request to delete task. userId: {} taskId: {}", userId, taskId);

        return taskHandler.processDeleteTaskRequest(userId, taskId);
    }

    @RequestMapping(method = GET, path = "api/user/{user_id}/task/{task_id}")
    public TaskResponse getTask(@PathVariable("user_id") Long userId,
                                @PathVariable("task_id") Long taskId) {
        log.debug("Received request to get task. userId: {} taskId: {}", userId, taskId);

        return taskHandler.processGetTaskRequest(userId, taskId);
    }

    //TODO Future improvement: add paging
    @RequestMapping(method = GET, path = "api/user/{user_id}/task")
    public TasksResponse getAllTasksForUser(@PathVariable("user_id") Long userId) {
        log.debug("Received request to list all tasks for user id {}", userId);

        return taskHandler.processGetAllTasksForUserRequest(userId);
    }
}
