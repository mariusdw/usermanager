package com.github.mariusdw.usermanager.web.controller;

import com.github.mariusdw.usermanager.web.handler.UserHandler;
import com.github.mariusdw.usermanager.web.request.CreateUserRequest;
import com.github.mariusdw.usermanager.web.request.UpdateUserRequest;
import com.github.mariusdw.usermanager.web.response.UserResponse;
import com.github.mariusdw.usermanager.web.response.UsersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
public class UserController {
    private UserHandler userHandler;

    @Autowired
    public UserController(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @RequestMapping(method = POST, path = "api/user")
    public UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        log.debug("Received request: {}", createUserRequest.toString());

        return this.userHandler.processCreateUserRequest(createUserRequest);
    }

    @RequestMapping(method = PUT, path = "api/user/{id}")
    public UserResponse updateUser(@PathVariable("id") Long userId,
                                   @RequestBody UpdateUserRequest updateUserRequest) {
        log.debug("Received request: {}", updateUserRequest.toString());

        return this.userHandler.processUpdateUserRequest(userId, updateUserRequest);
    }

    @RequestMapping(method = GET, path = "api/user/{id}")
    public UserResponse getUser(@PathVariable("id") Long userId) {
        log.debug("Received request to list user with id {}", userId);

        return this.userHandler.processGetUserRequest(userId);
    }

    //TODO Future improvement: add paging
    @RequestMapping(method = GET, path = "api/user")
    public UsersResponse getAllUsers() {
        log.debug("Received request to list all users");

        return this.userHandler.processGetAllUsersRequest();
    }
}
