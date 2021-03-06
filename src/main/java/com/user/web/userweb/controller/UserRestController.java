package com.user.web.userweb.controller;

import com.jasdom.validators.CountryPrefix;
import com.jasdom.validators.EmailValidator;
import com.jasdom.validators.PasswordChecker;
import com.jasdom.validators.PhoneValidator;
import com.user.web.userweb.Exception.ValidationException;
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
    public User registerUser(@RequestBody User user) throws ValidationException {
        validateUser(user);
        return service.add(user);
    }

    @PostMapping("/users/{userId}")
    public User updateUserById(@PathVariable int userId, @RequestBody User userInfo) throws ValidationException {
        if (!service.existsById(userId)) {
            throw new ValidationException(String.format("User with id '%s' does not exist", userId));
        }
        validateUser(userInfo);
        userInfo.setId(userId);
        return service.update(userInfo);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable int userId) {
        service.deleteById(userId);
    }

    @PostMapping("/phoneValidatorCountryPrefix")
    public void addPhoneValidatorCountryPrefix(@RequestBody CountryPrefix countryPrefix, @RequestParam String countryCode) throws ValidationException {
        if (!phoneValidator.addCountryPrefix(countryCode, countryPrefix.prefix, countryPrefix.length, countryPrefix.trunk)) {
            throw new ValidationException("Validation CountryPrefix is not valid");
        }
    }

    @PostMapping("/passwordChecker")
    public void setCustomPasswordCheckerRules(@RequestParam(value = "specialSymbols", required = false) String specialSymbols,
                                              @RequestParam(value = "minLength", required = false) Integer minLength,
                                              @RequestParam(value = "needsUppercase", required = false) Boolean needsUppercase) {
        if (specialSymbols != null) {
            passwordChecker.specialSymbols(specialSymbols);
        }
        if (minLength != null) {
            passwordChecker.minLength(minLength);
        }
        if (Boolean.TRUE.equals(needsUppercase)) {
            passwordChecker.needsUppercase();
        }
    }

    private void validateUser(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }
        if (!emailValidator.validateEmail(user.getEmail())) {
            throw new ValidationException("Users email is not valid");
        }
        if (!phoneValidator.validatePhoneNumber(user.getPhoneNumber())) {
            throw new ValidationException("Users phone number is not valid");
        }
        if (!passwordChecker.validatePassword(user.getPassword())) {
            throw new ValidationException("Users password is not valid");
        }
    }
}
