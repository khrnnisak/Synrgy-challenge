package com.example.FBJV24001115synergy7indbinfoodch7.dto.auth;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;


    public JwtResponseDTO(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    
}
