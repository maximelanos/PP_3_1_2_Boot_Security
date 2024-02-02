package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRESTController {

    private final UserService userService;
    @Autowired
    public AdminRESTController(UserService service) {
        this.userService = service;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> indexAll() {
        final List<User> userlist = userService.findAll();
        return new ResponseEntity<>(userlist, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User user) {
        boolean res = userService.saveUser(user);
        if (res) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> read(@PathVariable(value = "id") Long id) {
        User user = userService.read(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/users")
    public ResponseEntity<?> update(@RequestBody User user) {
        boolean res = userService.saveUserUpdate(user, user.getId());
        if (res) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        User fromDB = userService.read(id);
        if (fromDB != null) {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
