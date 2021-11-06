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
        repository.save(new User("Dominykas", "Daunoravicius", "862523452", "domdom52@gmail.com", "Gatve g. 1", "Password123!@#!"));
        repository.save(new User("Haris", "Hariauskas", "862345592", "tadishka@vu.mif.lt", "Gatve g. 2", "Pa3#%#ssword123"));
        repository.save(new User("Tomas", "Tomauskas", "865345675", "jonas@gmail.com", "Gatve g. 3", "Pa24s#%sword324"));
        repository.save(new User("Kasko", "Kalamas", "865345672", "petras@gmail.com", "Gatve g. 4", "1235Passw@$ord123"));
    }
}
