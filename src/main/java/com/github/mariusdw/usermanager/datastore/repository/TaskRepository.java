package com.github.mariusdw.usermanager.datastore.repository;

import com.github.mariusdw.usermanager.datastore.entity.TaskEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    List<TaskEntity> findByUserId(Long userId);

    @Query("SELECT t FROM TaskEntity t WHERE t.completed = false AND t.dateTime <= CURRENT_TIMESTAMP")
    List<TaskEntity> findIncompleteTasks();
}
