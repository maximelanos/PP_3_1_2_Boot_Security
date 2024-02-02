package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }


    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userDao.getUserByUsername(user.getUsername());

        System.out.println("userFromDB: " + userFromDB);
        if (userFromDB != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
        return true;
    }

    @Override
    public User read(Long id) {
        return userDao.read(id);
    }

    @Override
    public boolean saveUserUpdate(User user, Long id) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
        return true;
    }

    @Override
    public User delete(Long id) {
        return userDao.delete(id);
    }
}
