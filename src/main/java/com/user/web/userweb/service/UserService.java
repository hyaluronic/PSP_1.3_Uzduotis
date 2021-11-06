package com.user.web.userweb.service;

import com.user.web.userweb.model.User;
import com.user.web.userweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    public User findById(int id) {
        return repository.findById(id).get();
    }

    public User update(User user) {
        return repository.save(user);
    }

    public User add(User user) {
        repository.save(user);
        return user;
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public boolean existsById(int userId) {
        return repository.existsById(userId);
    }
}
