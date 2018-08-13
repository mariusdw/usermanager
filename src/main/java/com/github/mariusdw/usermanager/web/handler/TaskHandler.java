package com.github.mariusdw.usermanager.web.handler;

import com.github.mariusdw.usermanager.web.request.CreateTaskRequest;
import com.github.mariusdw.usermanager.web.request.UpdateTaskRequest;
import com.github.mariusdw.usermanager.web.response.BasicResponse;
import com.github.mariusdw.usermanager.web.response.TaskResponse;
import com.github.mariusdw.usermanager.web.response.TasksResponse;

public interface TaskHandler {
    TaskResponse processCreateTaskRequest(Long userId, CreateTaskRequest createTaskRequest);

    TaskResponse processUpdateTaskRequest(Long userId,
                                          Long taskId,
                                          UpdateTaskRequest updateTaskRequest);

    BasicResponse processDeleteTaskRequest(Long userId, Long taskId);

    TaskResponse processGetTaskRequest(Long userId, Long taskId);

    TasksResponse processGetAllTasksForUserRequest(Long userId);
}
