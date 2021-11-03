package com.user.web.userweb.jpa;

import com.user.web.userweb.model.User;
import com.user.web.userweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {
    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(new User("Mantas", "Jakaitis", "862569852", "mjakaitis18@gmail.com", "Adreso g. 1", "Password1!"));
        repository.save(new User("Tadas", "Tadiukinas", "862539592", "tadas@gmail.com", "Adreso g. 5", "Password2!"));
        repository.save(new User("Jonas", "Jonaitis", "865421345", "jonas@gmail.com", "Adreso g. 16", "Password3!"));
        repository.save(new User("Petras", "Petrauskas", "865974512", "petras@gmail.com", "Adreso g. 14", "Password4!"));
    }
}
