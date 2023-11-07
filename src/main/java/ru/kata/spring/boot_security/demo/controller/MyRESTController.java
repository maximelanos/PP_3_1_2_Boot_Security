package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public MyRESTController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> indexAll () {
        final List<User> userlist = userService.findAll();
        return new ResponseEntity<>(userlist, HttpStatus.OK);
    }

}
