package com.example.FBJV24001115synergy7indbinfoodch6.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.auth.RegisterRequestDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.user.UserDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.user.UserUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.User;

public interface UserService {

    UserDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO);
    void deleteUser(UUID id);
    List<User> getAllUsers();
    UserDTO getUserById(UUID id);
    Optional<User> isUserExist(String username, String email);
    User createUserPostLogin(String name, String email);

}
