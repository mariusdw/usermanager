package com.github.mariusdw.usermanager.web.handler;

import com.github.mariusdw.usermanager.datastore.entity.UserEntity;
import com.github.mariusdw.usermanager.datastore.repository.UserRepository;
import com.github.mariusdw.usermanager.web.request.CreateUserRequest;
import com.github.mariusdw.usermanager.web.request.UpdateUserRequest;
import com.github.mariusdw.usermanager.web.response.ResponseCode;
import com.github.mariusdw.usermanager.web.response.User;
import com.github.mariusdw.usermanager.web.response.UserResponse;
import com.github.mariusdw.usermanager.web.response.UsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultUserHandler implements UserHandler {
    private UserRepository userRepository;

    @Autowired
    public DefaultUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse processCreateUserRequest(CreateUserRequest createUserRequest) {
        UserResponse userResponse;

        String userName = createUserRequest.getUserName();
        String firstName = createUserRequest.getFirstName();
        String lastName = createUserRequest.getLastName();

        if (userName != null && firstName != null && lastName != null) {
            UserEntity userEntity = new UserEntity(userName, firstName, lastName);
            userRepository.save(userEntity);
            userResponse = new UserResponse(new User(userEntity));
        } else {
            userResponse = new UserResponse(ResponseCode.REQUEST_FORMAT_ERROR);
        }

        return userResponse;
    }

    @Override
    public UserResponse processUpdateUserRequest(Long userId, UpdateUserRequest updateUserRequest) {
        UserResponse userResponse;

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            boolean updated = false;
            if (updateUserRequest.getUserName() != null) {
                userEntity.setUsername(updateUserRequest.getUserName());
                updated = true;
            }
            if (updateUserRequest.getFirstName() != null) {
                userEntity.setFirstName(updateUserRequest.getFirstName());
                updated = true;
            }
            if (updateUserRequest.getLastName() != null) {
                userEntity.setLastName(updateUserRequest.getLastName());
                updated = true;
            }
            if (updated) {
                userRepository.save(userEntity);
            }
            userResponse = new UserResponse(new User(userEntity));
        } else {
            userResponse = new UserResponse(ResponseCode.USER_DOES_NOT_EXIST);
        }

        return userResponse;
    }

    @Override
    public UserResponse processGetUserRequest(Long userId) {
        UserResponse userResponse;

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userResponse = new UserResponse(new User(userEntity));
        } else {
            userResponse = new UserResponse(ResponseCode.USER_DOES_NOT_EXIST);
        }

        return userResponse;
    }

    @Override
    public UsersResponse processGetAllUsersRequest() {
        UsersResponse usersResponse;

        Iterable<UserEntity> userEntities = userRepository.findAll();
        List<User> users = new LinkedList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(new User(userEntity));
        }
        usersResponse = new UsersResponse(users);

        return usersResponse;
    }
}
