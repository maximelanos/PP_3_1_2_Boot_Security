package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;

public interface RoleService {

    void addRoles();
    Set<Role> getAllRoles();
    Set<Role> getSetOfRoles(String[] roleNames);
}
