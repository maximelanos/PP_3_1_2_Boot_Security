package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }

    @GetMapping
    public String indexAll(Model model, Principal principal) {

        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("addUser", new User());

        User userPrincipal = userService.getUserByUsername(principal.getName());
        model.addAttribute("userPrincipal", userPrincipal);
        model.addAttribute("userRoles", userPrincipal.getRoles());

        return "admin";
    }

    @PostMapping
    public String addUser(@ModelAttribute("addUser") @Valid User user,
                          @RequestParam(value = "roles") String[] roles) {

        roleService.addRoles();
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getUserForm(Model model, @PathVariable("id") Long id){

        model.addAttribute("user", userService.read(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           @PathVariable("id") Long id,
                           @RequestParam(value = "roles") String [] roles){

        user.setRoles(roleService.getSetOfRoles(roles));
        userService.saveUserUpdate(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping( "/{id}")
    public String deleteUser(@PathVariable("id") Long id){

        userService.delete(id);
        return "redirect:/admin";
    }
}
