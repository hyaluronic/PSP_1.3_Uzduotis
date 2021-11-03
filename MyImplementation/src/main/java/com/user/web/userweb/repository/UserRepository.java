package com.user.web.userweb.repository;

import com.user.web.userweb.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
