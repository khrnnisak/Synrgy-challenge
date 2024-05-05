package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.UUID;


import com.example.FBJV24001115synergy7indbinfoodch4.models.User;

public interface UserService {

    void createUser(String email, String username, String password);
    void updateUser(String username, String oldPassword, String newPassword);
    void deleteUser(String username);
    List<User> getAllUsers();
    UUID getUserId(String username);
    User getById(UUID id);

}
