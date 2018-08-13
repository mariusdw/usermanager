package com.github.mariusdw.usermanager.web.handler;

import com.github.mariusdw.usermanager.datastore.entity.TaskEntity;
import com.github.mariusdw.usermanager.datastore.entity.UserEntity;
import com.github.mariusdw.usermanager.datastore.repository.TaskRepository;
import com.github.mariusdw.usermanager.datastore.repository.UserRepository;
import com.github.mariusdw.usermanager.web.request.CreateTaskRequest;
import com.github.mariusdw.usermanager.web.request.UpdateTaskRequest;
import com.github.mariusdw.usermanager.web.response.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTaskHandlerTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;

    private Long validUserId = 1L;
    private Long validTaskId = 2L;

    private UserEntity validUserEntity;
    private TaskEntity validTaskEntity;
    private List<TaskEntity> validTaskEntities;

    private CreateTaskRequest validCreateTaskRequest;
    private UpdateTaskRequest validFullUpdateTaskRequest;

    private TaskHandler taskHandler;

    @Before
    public void init() {
        validUserEntity = new UserEntity(validUserId, "jsmith", "John", "Smith");
        validTaskEntity = new TaskEntity(validTaskId, validUserId, "OriginalTask", "Original task", Timestamp.from(ZonedDateTime.now().toInstant()), true);
        validTaskEntities = new ArrayList<>();
        validTaskEntities.add(validTaskEntity);

        validCreateTaskRequest = new CreateTaskRequest("OriginalTask", "Original task", Date.from(ZonedDateTime.now().toInstant()));
        validFullUpdateTaskRequest = new UpdateTaskRequest("UpdatedTask", "Updated task", Date.from(ZonedDateTime.now().toInstant()));

        taskHandler = new DefaultTaskHandler(userRepository, taskRepository);

        doReturn(Optional.of(validUserEntity)).when(userRepository).findById(validUserId);
        doReturn(Optional.of(validTaskEntity)).when(taskRepository).findById(validTaskId);
        doReturn(validTaskEntities).when(taskRepository).findByUserId(validUserId);
    }

    @Test
    public void testProcessCreateTaskRequest() {
        TaskResponse response = taskHandler.processCreateTaskRequest(validUserId, validCreateTaskRequest);

        verify(taskRepository).save(any(TaskEntity.class));
        assertEquals(ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), response.getDescription());
        Task task = response.getTask();
        assertEquals(validCreateTaskRequest.getName(), task.getName());
        assertEquals(validCreateTaskRequest.getDescription(), task.getDescription());
        assertEquals(validCreateTaskRequest.getDateTime(), task.getDateTime());
    }

    @Test
    public void testProcessUpdateTaskRequest() {
        TaskResponse response = taskHandler.processUpdateTaskRequest(validUserId, validTaskId, validFullUpdateTaskRequest);

        verify(taskRepository).save(any(TaskEntity.class));
        assertEquals(ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), response.getDescription());
        Task task = response.getTask();
        assertEquals(validFullUpdateTaskRequest.getName(), task.getName());
        assertEquals(validFullUpdateTaskRequest.getDescription(), task.getDescription());
        assertEquals(validFullUpdateTaskRequest.getDateTime(), task.getDateTime());
    }

    @Test
    public void testProcessDeleteTaskRequest() {
        BasicResponse response = taskHandler.processDeleteTaskRequest(validUserId, validTaskId);

        verify(taskRepository).delete(any(TaskEntity.class));
        assertEquals(ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), response.getDescription());
    }

    @Test
    public void testProcessGetTaskRequest() {
        TaskResponse response = taskHandler.processGetTaskRequest(validUserId, validTaskId);

        verify(taskRepository).findById(validTaskId);
        assertEquals(ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), response.getDescription());
        Task task = response.getTask();
        assertEquals(validTaskEntity.getId(), task.getId());
        assertEquals(validTaskEntity.getUserId(), task.getUserId());
        assertEquals(validTaskEntity.getName(), task.getName());
        assertEquals(validTaskEntity.getDescription(), task.getDescription());
        assertEquals(Date.from(validTaskEntity.getDateTime().toInstant()), task.getDateTime());
        assertEquals(validTaskEntity.getCompleted(), task.getCompleted());
    }

    @Test
    public void testProcessGetAllTasksForUserRequest() {
        TasksResponse response = taskHandler.processGetAllTasksForUserRequest(validUserId);

        verify(taskRepository).findByUserId(validUserId);
        assertEquals(ResponseCode.SUCCESS.getCode(), response.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), response.getDescription());
        List<Task> tasks = response.getTasksList();
        assertEquals(validTaskEntities.size(), tasks.size());
        Task task = tasks.get(0);
        assertEquals(validTaskEntity.getId(), task.getId());
        assertEquals(validTaskEntity.getUserId(), task.getUserId());
        assertEquals(validTaskEntity.getName(), task.getName());
        assertEquals(validTaskEntity.getDescription(), task.getDescription());
        assertEquals(Date.from(validTaskEntity.getDateTime().toInstant()), task.getDateTime());
        assertEquals(validTaskEntity.getCompleted(), task.getCompleted());
    }
}
