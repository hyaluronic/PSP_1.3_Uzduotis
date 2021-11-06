package com.user.web.userweb.repository;

import com.user.web.userweb.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "userRestRepository", collectionResourceRel = "user")
public interface UserRestRepository extends PagingAndSortingRepository<User, Integer> {
}
