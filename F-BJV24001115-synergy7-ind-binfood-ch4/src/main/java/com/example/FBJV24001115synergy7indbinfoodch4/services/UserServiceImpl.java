package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public void createUser(String email, String username, String password) {
        User user = userRepository.findByUsername(username);
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isPresent()) {
            throw new RuntimeException("Username telah tersedia!");
        }else{
            userRepository.userInsertData(email, username, password);
        }
    }

    @Override
    public void updateUser(String username, String oldPassword, String newPassword){
        User user = userRepository.findByUsername(username);
        Optional<User> userOptional = Optional.ofNullable(user);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Username tidak ditemukan!");
        }
        
        if (oldPassword.equals(user.getPassword())) {
            userRepository.userUpdateData(user.getId(), newPassword);
        }else{
            throw new RuntimeException("Password salah!");
        }
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        Optional<User> userOptional = Optional.ofNullable(user);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Username tidak ditemukan!");
        }
        UUID user_id = userOptional.get().getId();
        userRepository.userDeleteData(user_id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
