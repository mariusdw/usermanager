package com.github.mariusdw.usermanager.web.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserResponse extends BasicResponse {
    private User user;

    public UserResponse(ResponseCode responseCode) {
        super(responseCode);
    }

    public UserResponse(User user) {
        super(ResponseCode.SUCCESS);
        this.user = user;
    }
}
