package com.karim.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO {
    List<User> selectAllUsers();

    Optional<User> selectUserById(UUID userId);
}
