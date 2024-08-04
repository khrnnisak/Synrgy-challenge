package com.example.FBJV24001115synergy7indbinfoodch8.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FBJV24001115synergy7indbinfoodch8.dto.user.UserDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.dto.user.UserUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.models.accounts.User;
import com.example.FBJV24001115synergy7indbinfoodch8.services.MailService;
import com.example.FBJV24001115synergy7indbinfoodch8.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("user")
public class UserController {

    @Autowired UserService userService;
    @Autowired ModelMapper modelMapper;
    @Autowired MailService mailService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> showAllUser(){
        List<User> users = userService.getAllUsers();
        List<UserDTO> userList = users
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    
    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable("id") UUID id, @RequestBody UserUpdateDTO userUpdateDTO){
        Map<String, Object> response = new HashMap<>();
        UserDTO user =  userService.updateUser(id, userUpdateDTO);
        response.put("status", "suscces");
        response.put("data", user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Successfully deleted");


    }
    @GetMapping("by-id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") UUID id){
        UserDTO user =  userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    

    
}
