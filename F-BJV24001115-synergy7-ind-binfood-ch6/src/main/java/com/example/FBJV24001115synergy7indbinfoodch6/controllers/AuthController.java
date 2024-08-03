package com.example.FBJV24001115synergy7indbinfoodch6.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.FBJV24001115synergy7indbinfoodch6.dto.auth.JwtResponseDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.auth.LoginRequestDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.auth.RegisterRequestDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.User;
import com.example.FBJV24001115synergy7indbinfoodch6.security.JwtUtils;
import com.example.FBJV24001115synergy7indbinfoodch6.security.UserDetailsImpl;
import com.example.FBJV24001115synergy7indbinfoodch6.services.MailService;
import com.example.FBJV24001115synergy7indbinfoodch6.services.UserService;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired AuthenticationManager authenticationManager;
    @Autowired JwtUtils jwtUtils;
    @Autowired UserService userService;
    @Autowired MailService mailService;

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

    @GetMapping("/oauth2/success")
    public ResponseEntity<Map<String, Object>> googleLoginSuccess(Authentication authentication){
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String username = oidcUser.getName();
        String email = oidcUser.getEmail();
        User user = userService.isUserExist(email, username).orElseGet(() -> {
            return userService.createUserPostLogin(username, email);
        });
        Collection<GrantedAuthority> authorities = new ArrayList<>(oidcUser.getAuthorities());
        authorities.add(new SimpleGrantedAuthority(user.getRoles().getClass().getName())); 

        UserDetailsImpl modifiedUserDetails = UserDetailsImpl.build(oidcUser);
        OidcUser modifiedOidcUser = new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

        Authentication modifiedAuthentication = new UsernamePasswordAuthenticationToken(
            modifiedOidcUser,
            oidcUser.getIdToken(),
            authorities
        );

        String jwt = jwtUtils.generateToken(modifiedAuthentication);

        List<String> roles = modifiedAuthentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        JwtResponseDTO jwtResponse = new JwtResponseDTO(jwt, modifiedUserDetails.getUsername(), roles);
        data.put("jwt", jwtResponse);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
        
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        mailService.createUser(registerRequestDTO);
        return ResponseEntity.ok("User registered successfully. Please check your email for the OTP-verification.");
    }

    @PostMapping("/verified-user")
    public ResponseEntity<String> verifiedUser(@RequestParam String email, @RequestParam String otp) {
        boolean verified = mailService.verifiedUser(email, otp);
        if (verified) {
            return ResponseEntity.ok("User verification success.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        mailService.forgotPassword(email);
        return ResponseEntity.ok("Please check your email for the OTP-verification.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        boolean verified = mailService.resetPassword(email, otp, newPassword);
        if (verified) {
            return ResponseEntity.ok("Reset password success.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resend(@RequestParam String email) {
        mailService.generateOtp(email);
        return ResponseEntity.ok("Please check your email for the new OTP-verification.");
    }
}
