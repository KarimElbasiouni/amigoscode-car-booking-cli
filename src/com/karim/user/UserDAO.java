package com.karim.user;

import java.util.UUID;

public interface UserDAO {
    public User[] selectAllUsers();

    public User selectUserById(UUID userId);
}
