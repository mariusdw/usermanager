package com.github.mariusdw.usermanager.web.response;

import com.github.mariusdw.usermanager.datastore.entity.UserEntity;
import lombok.*;

@Getter
@ToString
public class User {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;

    public User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.userName = userEntity.getUsername();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
    }
}
