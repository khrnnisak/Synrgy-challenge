package com.example.FBJV24001115synergy7indbinfoodch6.dto.user;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String email_addres;
    private String password;
}
