package com.example.FBJV24001115synergy7indbinfoodch6.dto.auth;

import java.util.Set;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String emailAddress;
    private String password;
    private Set<String> roles;
}
