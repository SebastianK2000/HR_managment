package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;

import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // Other service methods...
}
