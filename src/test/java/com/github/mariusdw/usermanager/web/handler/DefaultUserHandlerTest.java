package com.github.mariusdw.usermanager.web.handler;

import com.github.mariusdw.usermanager.datastore.entity.UserEntity;
import com.github.mariusdw.usermanager.datastore.repository.UserRepository;
import com.github.mariusdw.usermanager.web.request.CreateUserRequest;
import com.github.mariusdw.usermanager.web.request.UpdateUserRequest;
import com.github.mariusdw.usermanager.web.response.ResponseCode;
import com.github.mariusdw.usermanager.web.response.User;
import com.github.mariusdw.usermanager.web.response.UserResponse;
import com.github.mariusdw.usermanager.web.response.UsersResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserHandlerTest {
    @Mock
    private UserRepository userRepository;

    private Long validUserId = 1L;

    private CreateUserRequest validCreateUserRequest;
    private UpdateUserRequest validUpdateUserRequest;

    private UserEntity validUserEntity;
    private List<UserEntity> validUserEntities;

    private UserHandler userHandler;

    @Before
    public void init() {
        validUserEntity = new UserEntity(validUserId, "jsmith", "John", "Smith");
        validUserEntities = new LinkedList<>();
        validUserEntities.add(validUserEntity);

        validCreateUserRequest = new CreateUserRequest("jsmith", "John", "Smith");
        validUpdateUserRequest = new UpdateUserRequest("gbrown", "Gerald", "Brown");

        userHandler = new DefaultUserHandler(userRepository);

        doReturn(Optional.of(validUserEntity)).when(userRepository).findById(validUserId);
        doReturn(validUserEntities).when(userRepository).findAll();
    }

    @Test
    public void testProcessCreateUserRequest() {
        UserResponse userResponse = userHandler.processCreateUserRequest(validCreateUserRequest);

        verify(userRepository).save(any(UserEntity.class));
        assertEquals(ResponseCode.SUCCESS.getCode(), userResponse.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), userResponse.getDescription());
        User user = userResponse.getUser();
        assertNotNull(user);
        assertEquals(validCreateUserRequest.getUserName(), user.getUserName());
        assertEquals(validCreateUserRequest.getFirstName(), user.getFirstName());
        assertEquals(validCreateUserRequest.getLastName(), user.getLastName());
    }

    @Test
    public void testProcessUpdateUserRequest() {
        UserResponse userResponse = userHandler.processUpdateUserRequest(validUserId, validUpdateUserRequest);

        verify(userRepository).save(any(UserEntity.class));
        assertEquals(ResponseCode.SUCCESS.getCode(), userResponse.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), userResponse.getDescription());
        User user = userResponse.getUser();
        assertNotNull(user);
        assertEquals(validUpdateUserRequest.getUserName(), user.getUserName());
        assertEquals(validUpdateUserRequest.getFirstName(), user.getFirstName());
        assertEquals(validUpdateUserRequest.getLastName(), user.getLastName());
    }

    @Test
    public void testGetUserRequest() {
        UserResponse userResponse = userHandler.processGetUserRequest(validUserId);

        verify(userRepository).findById(validUserId);
        assertEquals(ResponseCode.SUCCESS.getCode(), userResponse.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), userResponse.getDescription());
        User user = userResponse.getUser();
        assertNotNull(user);
        assertEquals(validUserId, user.getId());
        assertEquals(validUserEntity.getUsername(), user.getUserName());
        assertEquals(validUserEntity.getFirstName(), user.getFirstName());
        assertEquals(validUserEntity.getLastName(), user.getLastName());
    }

    @Test
    public void testProcessGetAllUsersRequest() {
        UsersResponse usersResponse = userHandler.processGetAllUsersRequest();

        verify(userRepository).findAll();
        assertEquals(ResponseCode.SUCCESS.getCode(), usersResponse.getCode());
        assertEquals(ResponseCode.SUCCESS.getDescription(), usersResponse.getDescription());
        assertEquals(validUserEntities.size(), usersResponse.getUserList().size());
        User user = usersResponse.getUserList().get(0);
        assertEquals(validUserId, user.getId());
        assertEquals(validUserEntity.getUsername(), user.getUserName());
        assertEquals(validUserEntity.getFirstName(), user.getFirstName());
        assertEquals(validUserEntity.getLastName(), user.getLastName());
    }
}
