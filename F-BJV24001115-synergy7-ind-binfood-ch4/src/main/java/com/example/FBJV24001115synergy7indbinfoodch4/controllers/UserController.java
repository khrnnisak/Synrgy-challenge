package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.services.UserService;

public class UserController {

    @Autowired
    UserService userService;

    public void showAllUser(){
        List<User> users = userService.getAllUsers();
        users.forEach(user -> System.out.println(user.getUsername()));
    }

    public void createUser(String email, String username, String password){
        userService.createUser(email, username, password);
    }

    public void updateUser(String username, String oldPassword, String newPassword){
        userService.updateUser(username, oldPassword, newPassword);
        showAllUser();
    }

    public void deleteUser(String username){
        userService.deleteUser(username);
        showAllUser();
    }

}
