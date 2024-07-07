package com.example.FBJV24001115synergy7indbinfoodch6.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.auth.RegisterRequestDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.user.UserCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.user.UserDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.user.UserUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.Role;
import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.Role.ERole;
import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.User;
import com.example.FBJV24001115synergy7indbinfoodch6.repositories.RoleRepository;
import com.example.FBJV24001115synergy7indbinfoodch6.repositories.UserRepository;
import com.example.FBJV24001115synergy7indbinfoodch6.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class UserServiceImpl implements UserService{
    @Autowired UserRepository userRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired RoleRepository roleRepository;

    

    @Override
    public Optional<User> isUserExist(String email, String username) {
        return userRepository.findByEmailandUsername(email, username);
    }
    @Override
    public UserDTO createUser(RegisterRequestDTO registerRequestDTO) {
         try {
            Set<Role> roles = new HashSet<>();
            Set<String> reqRoles = registerRequestDTO.getRole();
            if (reqRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            }else{
                reqRoles.forEach(role -> {
                    if (role.equalsIgnoreCase("role_merchant")) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_MERCHANT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }else{
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                });
            }
            User user = User.builder()
                .email_addres(registerRequestDTO.getEmail_addres())
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .roles(roles)
                .build();
            userRepository.save(user);
            log.info(FormatMessageUtil.succesToAddMessage());
            return modelMapper.map(user, UserDTO.class);
            
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
