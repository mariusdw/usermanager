package com.github.mariusdw.usermanager.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UsersResponse extends BasicResponse {
    @JsonProperty("users")
    private List<User> userList;

    public UsersResponse(ResponseCode responseCode) {
        super(responseCode);
    }

    public UsersResponse(List<User> users) {
        super(ResponseCode.SUCCESS);
        this.userList = users;
    }
}
