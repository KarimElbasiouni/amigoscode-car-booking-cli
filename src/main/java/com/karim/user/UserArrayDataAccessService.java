package com.karim.user;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.csv.*;

public class UserArrayDataAccessService implements UserDAO{
    public static final CSVFormat format = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .get();


    private static final List<User> users;
    static{
        users = new ArrayList<>();
        try(InputStream in = (UserArrayDataAccessService.class).getClassLoader().getResourceAsStream("users.csv");
            CSVParser csvParser = CSVParser.parse(in, StandardCharsets.UTF_8, format)
        ){
            csvParser.stream()
                    .forEach(record->{
                            String id = record.get("id");
                            String name = record.get("name");

                            if(id == null || id.isBlank() || name == null || name.isBlank()){
                                System.out.println("[INFO] Skipped CSV record from with record number: " + record.getRecordNumber());
                                return;
                            }
                            users.add(new User(UUID.fromString(id), name));
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            throw new NullPointerException();
        }

    };

    public List<User> selectAllUsers(){
        return new ArrayList<>(users);
    }

    public Optional<User> selectUserById(UUID userId){
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
    }
}
