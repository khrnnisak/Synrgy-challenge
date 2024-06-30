package com.example.FBJV24001115synergy7indbinfoodch6.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.FBJV24001115synergy7indbinfoodch6.dto.auth.JwtResponseDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.auth.LoginRequestDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.security.JwtUtils;
import com.example.FBJV24001115synergy7indbinfoodch6.security.UserDetailsImpl;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired AuthenticationManager authenticationManager;
    @Autowired JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody LoginRequestDTO loginRequest){
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken
            (loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        JwtResponseDTO JwtResponse = new JwtResponseDTO(jwt, userDetails.getUsername(), roles);
        data.put("jwt", JwtResponse);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
