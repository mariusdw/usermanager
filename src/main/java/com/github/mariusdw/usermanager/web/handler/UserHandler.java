package com.github.mariusdw.usermanager.web.handler;

import com.github.mariusdw.usermanager.web.request.CreateUserRequest;
import com.github.mariusdw.usermanager.web.request.UpdateUserRequest;
import com.github.mariusdw.usermanager.web.response.UserResponse;
import com.github.mariusdw.usermanager.web.response.UsersResponse;

public interface UserHandler {
    UserResponse processCreateUserRequest(CreateUserRequest createUserRequest);

    UserResponse processUpdateUserRequest(Long userId, UpdateUserRequest updateUserRequest);

    UserResponse processGetUserRequest(Long userId);

    UsersResponse processGetAllUsersRequest();
}
