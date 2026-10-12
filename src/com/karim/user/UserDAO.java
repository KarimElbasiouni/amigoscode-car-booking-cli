package com.karim.user;


import java.util.UUID;

public class UserDAO {

    private static User[] users;
    static{
        users = new User[]{
                new User(UUID.fromString("9b1ea9e7-51df-45b2-a3c9-1684626d0858"), "Michael"),
                new User(UUID.fromString("8b4f0862-12a9-4baa-a719-3f0c17f388a1"), "Omar"),
                new User(UUID.fromString("2fad0ab7-f112-4e04-acd0-d533d0e051ac"), "Ian"),
                new User(UUID.fromString("3eb945eb-9ff7-49a8-9187-a315aad37f3e"), "Fatima"),
                new User(UUID.fromString("07ff3906-2fff-416e-87ee-0571d44ebf26"), "Nelson")
        };
    }

    public User[] selectAllUsers(){
        return users;
    }

    public User selectUserById(UUID userId){
        for (User user : users) {
            if(user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }
}
