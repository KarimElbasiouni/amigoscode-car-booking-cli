package com.karim.user;

import java.util.UUID;

public class UserService {
    private final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

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
