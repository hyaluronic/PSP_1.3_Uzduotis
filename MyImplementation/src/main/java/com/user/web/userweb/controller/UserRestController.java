package com.user.web.userweb.controller;

import com.jasdom.validators.CountryPrefix;
import com.jasdom.validators.EmailValidator;
import com.jasdom.validators.PasswordChecker;
import com.jasdom.validators.PhoneValidator;
import com.user.web.userweb.model.User;
import com.user.web.userweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    UserService service;

    EmailValidator emailValidator = new EmailValidator();
    PhoneValidator phoneValidator = new PhoneValidator();
    PasswordChecker passwordChecker = new PasswordChecker();

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }


    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable int userId) {
        return service.findById(userId);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        if (user == null ||
                !emailValidator.validateEmail(user.getEmail()) ||
                !phoneValidator.validatePhoneNumber(user.getPhoneNumber()) ||
                !passwordChecker.validatePassword(user.getPassword())
        ) {
            throw new NullPointerException("");
        }
        return service.add(user);
    }

    @PostMapping("/users/{userId}")
    public User updateUserById(@PathVariable int userId, @RequestBody User userInfo) {
        if (userInfo == null ||
                !service.existsById(userId) ||
                !emailValidator.validateEmail(userInfo.getEmail()) ||
                !phoneValidator.validatePhoneNumber(userInfo.getPhoneNumber()) ||
                !passwordChecker.validatePassword(userInfo.getPassword())) {
            throw new NullPointerException("");
        }
        userInfo.setId(userId);
        service.update(userInfo);
        return userInfo;
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable int userId) {
        service.deleteById(userId);
    }

    @PostMapping("/phoneValidatorCountryPrefix")
    public void addPhoneValidatorCountryPrefix(@RequestBody CountryPrefix countryPrefix, @RequestParam String countryCode) {
        phoneValidator.addCountryPrefix(countryCode, countryPrefix.prefix, countryPrefix.length, countryPrefix.trunk);
    }
}
