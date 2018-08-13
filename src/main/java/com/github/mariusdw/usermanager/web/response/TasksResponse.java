package com.github.mariusdw.usermanager.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@ToString
public class TasksResponse extends BasicResponse {
    @JsonProperty("tasks")
    private List<Task> tasksList;

    public TasksResponse(ResponseCode responseCode) {
        super(responseCode);
    }

    public TasksResponse(List<Task> tasks) {
        super(ResponseCode.SUCCESS);
        this.tasksList = tasks;
    }
}
