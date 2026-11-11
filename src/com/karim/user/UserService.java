package com.karim.user;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public User getUserById(UUID id) {
        return userDao.selectUserById(id).orElseThrow(() -> new RuntimeException("User does not exist"));

    }

    public List<User> getAllUsers(){
        return userDao.selectAllUsers();
    }
}
