package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class MainController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "registration";
    }

    @Transactional
    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam(value = "roles") String[] roles) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        roleService.addRoles();
        user.setRoles(roleService.getSetOfRoles(roles));
        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        model.addAttribute("error", error!=null);
        model.addAttribute("logout", logout!=null);

        return "login";
    }

    @GetMapping("/admin")
    public String showAllUser(Model model) {
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @GetMapping("/user")
    public String showOneUser() {
        return "user-info";
    }
}
