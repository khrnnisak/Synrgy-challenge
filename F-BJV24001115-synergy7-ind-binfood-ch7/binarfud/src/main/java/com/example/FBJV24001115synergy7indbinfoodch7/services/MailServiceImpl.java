package com.example.FBJV24001115synergy7indbinfoodch7.services;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.auth.RegisterRequestDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.user.UserDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.models.OTP;
import com.example.FBJV24001115synergy7indbinfoodch7.models.accounts.Role;
import com.example.FBJV24001115synergy7indbinfoodch7.models.accounts.User;
import com.example.FBJV24001115synergy7indbinfoodch7.models.accounts.Role.ERole;
import com.example.FBJV24001115synergy7indbinfoodch7.repositories.OTPRepository;
import com.example.FBJV24001115synergy7indbinfoodch7.repositories.RoleRepository;
import com.example.FBJV24001115synergy7indbinfoodch7.repositories.UserRepository;
import com.example.FBJV24001115synergy7indbinfoodch7.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImpl implements MailService{
    @Autowired Environment env;
    @Autowired JavaMailSender javaMailSender;
    @Autowired RoleRepository roleRepository;
    @Autowired UserRepository userRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired OTPRepository otpRepository;

    @Override
    public UserDTO createUser(RegisterRequestDTO registerRequestDTO) {
        try {
            if (userRepository.findByEmailAddress(registerRequestDTO.getEmailAddress()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
            }

            if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exist");
            }

            Set<Role> roles = new HashSet<>();
            Set<String> reqRoles = registerRequestDTO.getRoles();
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
                .emailAddress(registerRequestDTO.getEmailAddress())
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .roles(roles)
                .build();
            userRepository.save(user);
            generateOtp(registerRequestDTO.getEmailAddress());
            log.info(FormatMessageUtil.succesToAddMessage());
            return modelMapper.map(user, UserDTO.class);
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public void sendMail(String emailAddress, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(emailAddress);
        message.setSubject("Binarfud OTP");
        message.setText(otp);
        javaMailSender.send(message);
    }

    @Override
    public void generateOtp(String emailAddress) {
        Optional<OTP> exisOtp = otpRepository.findByEmailAddress(emailAddress);
        if (exisOtp.isPresent()) {
            otpRepository.delete(exisOtp.get());
        }
        String otp = String.valueOf(new Random().nextInt(999999));
        OTP newOtp = OTP.builder()
            .emailAddress(emailAddress)
            .otp(otp)
            .expiredTime(LocalDateTime.now().plusMinutes(5))
            .build();
        otpRepository.save(newOtp);
        sendMail(emailAddress, otp);
    }

    @Override
    public boolean validateOtp(String email, String otp) {

        try {
            LocalDateTime now = LocalDateTime.now();
            Optional<OTP> otpEntity = otpRepository.findByEmailAddressandOtp(email, otp);
            if (otpEntity.isPresent()) {
                if (otpEntity.get().getExpiredTime().isAfter(now)) {
                    otpRepository.deleteById(otpEntity.get().getId());
                    return true;
                }else{
                    otpRepository.deleteById(otpEntity.get().getId());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP has expired.");
                }
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
            }
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public void forgotPassword(String email) {
        try {
        userRepository.findByEmailAddress(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        generateOtp(email);
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }

        
    }

    @Override
    public boolean verifiedUser(String email, String otp) {
        try {
            boolean validateOtp = validateOtp(email, otp);
            if (validateOtp) {
                User user = userRepository.findByEmailAddress(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
                user.setVerified(true);
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }

    }

    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        try {
            boolean validateOtp = validateOtp(email, otp);
            if (validateOtp) {
                User user = userRepository.findByEmailAddress(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    


    

}
