package com.example.FBJV24001115synergy7indbinfoodch5.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch5.dto.user.UserDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.user.UserUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.user.UserCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch5.models.User;

public interface UserService {

    UserDTO createUser(UserCreateDTO userRequestDTO);
    UserDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO);
    void deleteUser(UUID id);
    List<User> getAllUsers();
    UserDTO getUserById(UUID id);

}
