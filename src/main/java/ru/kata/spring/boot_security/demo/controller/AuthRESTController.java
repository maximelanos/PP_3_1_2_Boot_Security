package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@RestController
@RequestMapping("/userApi")
public class AuthRESTController {

    private final UserService userService;

    public AuthRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth")
    public ResponseEntity<User> getAuthUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
