package com.github.mariusdw.usermanager.datastore.repository;

import com.github.mariusdw.usermanager.datastore.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
