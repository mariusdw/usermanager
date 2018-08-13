package com.github.mariusdw.usermanager.web.response;

public enum ResponseCode {
    SUCCESS(0, "Success"),
    REQUEST_FORMAT_ERROR(1, "Request message was incorrectly formatted"),
    TASK_NOT_LINKED_TO_USER(2, "Task not linked to selected user"),
    USER_DOES_NOT_EXIST(3, "User does not exist"),
    TASK_DOES_NOT_EXIST(4, "Task does not exist");

    private Integer code;
    private String description;

    ResponseCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
