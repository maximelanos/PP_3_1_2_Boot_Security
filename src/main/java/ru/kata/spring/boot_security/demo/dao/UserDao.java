package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    User getUserByUsername(String username);
    List<User> findAll();
    void create(User user);
    User read(Long id);
    void update(User user);
    User delete(Long id);
}
