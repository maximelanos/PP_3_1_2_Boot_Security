package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

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
    public String indexAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/user-info/{id}")
    public String profileUser(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.read(id));
        return "user-info";
    }

    @GetMapping("/user-edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.read(id));
        return "user-update";
    }

    @PatchMapping("/user-update")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult,
                           Model model,
                           @RequestParam(value = "roles") String [] roles){

        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "user-update";
        }
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.saveUserUpdate(user);
        return "redirect:/admin";
    }

    @GetMapping( "/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
