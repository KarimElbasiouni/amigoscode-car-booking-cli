package com.karim.user;

import java.util.List;
import java.util.UUID;

public interface UserDAO {
    public List<User> selectAllUsers();

    public User selectUserById(UUID userId);
}
