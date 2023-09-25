package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;

public interface RoleDao {

    void addRoles();
    Set<Role> getAllRoles();
    Set<Role> getSetOfRoles(String[] roleNames);
    Role getRoleByName(String role);
}
