package com.github.mariusdw.usermanager.web.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TaskResponse extends BasicResponse {
    private Task task;

    public TaskResponse(ResponseCode responseCode) {
        super(responseCode);
    }

    public TaskResponse(Task task) {
        super(ResponseCode.SUCCESS);
        this.task = task;
    }
}
