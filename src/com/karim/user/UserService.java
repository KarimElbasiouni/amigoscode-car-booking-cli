package com.karim.user;

import java.util.UUID;

public class UserService {
    private UserDAO userDao = new UserDAO();

    public User getUserById(UUID id) {
        User user = userDao.selectUserById(id);
        if(user == null){
            throw new RuntimeException("User does not exist");

        }
         return user;

    }

    public User[] getAllUsers(){
        // no checks since there is No CRUD functionality for Users
        return userDao.selectAllUsers();
    }
}
