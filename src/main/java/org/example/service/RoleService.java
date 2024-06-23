package org.example.service;

import org.example.dao.RoleDAO;
import org.example.model.Role;

import java.util.List;

public class RoleService {

    private RoleDAO roleDAO = new RoleDAO();

    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

}
