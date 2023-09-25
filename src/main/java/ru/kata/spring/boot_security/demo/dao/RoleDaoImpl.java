package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.ListRoles;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Set<Role> getAllRoles() {
        List<Role> roleList = em.createQuery("select r from Role r ", Role.class).getResultList();
        return new HashSet<>(roleList);
    }

    @Override
    public Set<Role> getSetOfRoles(String[] roleNames) {
        Set<Role> roleSet = new HashSet<>();
        for (String name : roleNames) {
            roleSet.add(getRoleByName(name));
        }
        return roleSet;
    }

    @Override
    public Role getRoleByName(String name) {
        try {
            return em.createQuery("SELECT r from Role r where r.name=:name", Role.class)
                    .setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Метод, связанный с Enum
     */
    @Override
    public void addRoles() {
        for (ListRoles listRoles : ListRoles.values()) {
            if (getRoleByName(listRoles.toString()) == null) {
                em.persist(new Role(listRoles.toString()));
            }
        }
    }
}
