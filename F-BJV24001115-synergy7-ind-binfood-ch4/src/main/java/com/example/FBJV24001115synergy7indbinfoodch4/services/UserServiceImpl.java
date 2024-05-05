package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.UserRepository;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public void createUser(String email, String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            Optional<User> userOptional = Optional.ofNullable(user);
            if (userOptional.isPresent()) {
                log.error(FormatMessageUtil.duplicateMessage());
            }else{
                userRepository.userInsertData(email, username, password);
                log.info(FormatMessageUtil.succesToAddMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage());
        } 
    }

    @Override
    public void updateUser(String username, String oldPassword, String newPassword){
        try {
            User user = userRepository.findByUsername(username);
            Optional<User> userOptional = Optional.ofNullable(user);
            if (!userOptional.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Username tidak ditemukan!"));
            }else if (oldPassword.equals(user.getPassword())) {
                userRepository.userUpdateData(user.getId(), newPassword);
            }else{
                System.out.println(FormatMessageUtil.errorMessageFormat("Password Salah!"));
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToEditMessage());
        } 
    }

    @Override
    public void deleteUser(String username) {
        try {
            User user = userRepository.findByUsername(username);
            Optional<User> userOptional = Optional.ofNullable(user);
            if (!userOptional.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                UUID user_id = userOptional.get().getId();
                userRepository.userDeleteData(user_id);
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToDeleteMessage());
        } 
    }

    @Override
    public UUID getUserId(String username) {
        try {
            Optional<String> opt = Optional.ofNullable(username);
            if (!opt.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }
            Optional<User> existUser = Optional.ofNullable(userRepository.findByUsername(username));
            if (!existUser.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                User choosenUser = existUser.get();
                return choosenUser.getId();
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.notFoundMessage());
        } 
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getById(UUID id) {
        Optional<UUID> opt = Optional.ofNullable(id);
            if (!opt.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

}
