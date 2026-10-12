package com.karim.user;

import java.util.Arrays;
import java.util.UUID;

public class UserService {
    private UserDAO userDao = new UserDAO();

    public User getUserById(UUID id) {
         return userDao.selectUserById(id);

    }

    public User[] getAllUsers(){
        // no checks since there is No CRUD functionality for Users
        return userDao.selectAllUsers();
    }
}
