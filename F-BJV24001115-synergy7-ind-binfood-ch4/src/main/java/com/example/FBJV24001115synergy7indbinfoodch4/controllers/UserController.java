package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.services.UserService;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.UserView;

@Controller
public class UserController {

    @Autowired UserService userService;
    @Autowired UserView userView;
    @Autowired MainView mainView;

    public List<User> showAllUser(){
        return userService.getAllUsers();
        // users.forEach(user -> System.out.println(user.getUsername()));
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

    public UUID getUserId(String username){
        return userService.getUserId(username);
    }

    public void userMenuSelection(int choice){
        switch (choice) {
            case 1:
                userView.createView();
                break;
            case 2:
                userView.updateView();
                break;
            case 3:
                userView.deleteView();
                break;
            case 0:
                mainView.displayView();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                mainView.displayView();
                break;
        }
    }
}
