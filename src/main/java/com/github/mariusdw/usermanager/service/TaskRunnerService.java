package com.github.mariusdw.usermanager.service;

import com.github.mariusdw.usermanager.datastore.entity.TaskEntity;
import com.github.mariusdw.usermanager.datastore.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class TaskRunnerService {
    @Value("${com.github.mariusdw.usermanager.scheduled_task_interval_in_seconds}")
    private Long scheduled_interval_in_seconds;

    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private TaskRepository taskRepository;
    private AtomicBoolean busy;

    @Autowired
    public TaskRunnerService(@Qualifier("taskScheduler") ThreadPoolTaskScheduler threadPoolTaskScheduler,
                             TaskRepository taskRepository) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.taskRepository = taskRepository;
        this.busy = new AtomicBoolean(false);
    }

    @PostConstruct
    public void init() {
        this.threadPoolTaskScheduler.scheduleAtFixedRate(() -> {
            log.debug("Checking for outstanding tasks every {} seconds", scheduled_interval_in_seconds);
            if (!this.busy.getAndSet(true)) {
                List<TaskEntity> taskEntities = taskRepository.findIncompleteTasks();
                if (taskEntities != null && !taskEntities.isEmpty()) {
                    for (TaskEntity taskEntity : taskEntities) {
                        log.info("Ran Task {} at {}", taskEntity, LocalDateTime.now());
                        taskEntity.setCompleted(true);
                        taskRepository.save(taskEntity);
                    }
                } else {
                    log.debug("No outstanding tasks found");
                }
                this.busy.set(false);
            } else {
                log.debug("Skipped check due to still being busy with a previous check");
            }
        }, Duration.ofSeconds(scheduled_interval_in_seconds));
    }
}
