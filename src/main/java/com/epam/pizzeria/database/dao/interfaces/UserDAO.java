package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.User;

import java.util.List;

public interface UserDAO {
    void insertUser(User user);
    List<User> getAllUsers();
    User getUserById(Long userId);
    User getUserByEmailPassword(String email, String password);
    void updateUserAddress(User user);
    void updateUserPhoneNumber(User user);
    void updateUserBanStatus(User user);
    void updateUserPassword(Long userId, String newPassword);
}
