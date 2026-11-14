package com.karim.user;

import net.datafaker.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFakerDataAccessService implements UserDAO{
    private static final List<User> users;
    static {
        System.out.println("CarBookingCLI: FAKER VERSION");
        System.out.println("List of Generated Users:");
        users = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            users.add(new User(UUID.randomUUID(), faker.name().firstName() ));
        }
    }
    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public Optional<User> selectUserById(UUID userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
    }
}
