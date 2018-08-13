package com.github.mariusdw.usermanager.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mariusdw.usermanager.datastore.entity.TaskEntity;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class Task {
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    private String name;
    private String description;
    @JsonProperty("date_time")
    private Date dateTime;
    private Boolean completed;

    public Task(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.userId = taskEntity.getUserId();
        this.name = taskEntity.getName();
        this.description = taskEntity.getDescription();
        this.dateTime = Date.from(taskEntity.getDateTime().toInstant());
        this.completed = taskEntity.getCompleted();
    }
}
