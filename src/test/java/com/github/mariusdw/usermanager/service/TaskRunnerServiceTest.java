package com.github.mariusdw.usermanager.service;

import com.github.mariusdw.usermanager.datastore.entity.TaskEntity;
import com.github.mariusdw.usermanager.datastore.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TaskRunnerServiceTest {

    @Mock
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Mock
    private TaskRepository taskRepository;

    private TaskRunnerService taskRunnerService;
    private TaskEntity validIncompleteTaskEntity;
    private List<TaskEntity> tasks;

    @Before
    public void init() {
        taskRunnerService = new TaskRunnerService(threadPoolTaskScheduler, taskRepository);
        ReflectionTestUtils.setField(taskRunnerService, "scheduled_interval_in_seconds", 10L);
        validIncompleteTaskEntity = new TaskEntity(1L, 1L, "ValidTask", "Valid task", Timestamp.from(ZonedDateTime.now().toInstant()), false);
        tasks = new LinkedList<>();
        tasks.add(validIncompleteTaskEntity);

        doReturn(tasks).when(taskRepository).findIncompleteTasks();
    }

    @Test
    public void testTaskRunnerService() {
        doAnswer((inv) -> {
            ((Runnable) inv.getArgument(0)).run();
            return null;
        }).when(threadPoolTaskScheduler).scheduleAtFixedRate(any(), any());

        final TaskEntity[] savedEntity = new TaskEntity[]{null};
        doAnswer((inv) -> {
            savedEntity[0] = inv.getArgument(0);
            return savedEntity[0];
        }).when(taskRepository).save(any());

        taskRunnerService.init();

        assertEquals(validIncompleteTaskEntity.getId(), savedEntity[0].getId());
        assertEquals(validIncompleteTaskEntity.getUserId(), savedEntity[0].getUserId());
        assertEquals(validIncompleteTaskEntity.getName(), savedEntity[0].getName());
        assertEquals(validIncompleteTaskEntity.getDescription(), savedEntity[0].getDescription());
        assertEquals(validIncompleteTaskEntity.getDateTime(), savedEntity[0].getDateTime());
        assertTrue(savedEntity[0].getCompleted());
    }
}
