package com.example.FBJV24001115synergy7indbinfoodch5.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch5.dto.user.UserDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.user.UserUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.user.UserCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.models.User;
import com.example.FBJV24001115synergy7indbinfoodch5.repositories.UserRepository;
import com.example.FBJV24001115synergy7indbinfoodch5.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class UserServiceImpl implements UserService{
    @Autowired UserRepository userRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserCreateDTO userRequestDTO) {
         try {
            User existUser = userRepository.findByEmailAddress(userRequestDTO.getEmail_addres());
            Optional<User> userOptional = Optional.ofNullable(existUser);
            if (userOptional.isPresent()) {
                log.error(FormatMessageUtil.duplicateMessage());
            }else{
                User user = User.builder()
                    .email_addres(userRequestDTO.getEmail_addres())
                    .username(userRequestDTO.getUsername())
                    .password(userRequestDTO.getPassword())
                    .build();
                userRepository.userInsertData(user.getEmail_addres(), user.getUsername(), user.getPassword());
                log.info(FormatMessageUtil.succesToAddMessage());
                return modelMapper.map(user, UserDTO.class);

            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToAddMessage());
        } 
        return null;
    }

    @Override
    public UserDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO){
        try {
            Optional<User> existUser = userRepository.findById(id);
            if (!existUser.isPresent()) {
                log.error(FormatMessageUtil.errorMessageFormat("Username tidak ditemukan!"));
            }else{
                if (userUpdateDTO.getPassword().equals(userUpdateDTO.getPassword())) {
                    userRepository.userUpdateData(id, userUpdateDTO.getNewPassword());
                    log.info(FormatMessageUtil.succesToEditMessage());
                    Optional<User> user = userRepository.findById(id);
                    return modelMapper.map(user.get(), UserDTO.class);
                }else{
                    log.error(FormatMessageUtil.errorMessageFormat("Password Salah!"));
                }
            } 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage());
        } 
        return null;
    }

    @Override
    public void deleteUser(UUID id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{
                userRepository.userDeleteData(id);
                log.info(FormatMessageUtil.succesToDeleteMessage());
            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToDeleteMessage());
        } 
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO getUserById(UUID id) {
        try {
            Optional<UUID> opt = Optional.ofNullable(id);
            if (!opt.isPresent()) {
                log.error(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }
            Optional<User> existUser = userRepository.findById(id);
            if (!existUser.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{
                User user = existUser.get();
                return modelMapper.map(user, UserDTO.class);
            }
    
        } catch (Exception e) {
            log.error(FormatMessageUtil.notFoundMessage());
        }
       
        return null;
    }

}
