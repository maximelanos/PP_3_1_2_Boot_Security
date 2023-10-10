package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User getUserByUsername(String username);
    List<User> findAll();
    boolean saveUser(User user);
    User read(Long id);
    boolean saveUserUpdate(User user, Long id);
    User delete(Long id);
}
