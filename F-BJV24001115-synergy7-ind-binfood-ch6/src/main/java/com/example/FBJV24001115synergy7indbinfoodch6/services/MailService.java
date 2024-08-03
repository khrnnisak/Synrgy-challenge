package com.example.FBJV24001115synergy7indbinfoodch6.services;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.auth.RegisterRequestDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.user.UserDTO;

public interface MailService {

    UserDTO createUser(RegisterRequestDTO registerRequestDTO);
    void sendMail(String emailAddress, String otp);
    void generateOtp(String emailAddress);
    boolean validateOtp(String email, String otp);
    void forgotPassword(String enail);
    boolean verifiedUser(String email, String otp);
    boolean resetPassword(String email, String otp, String newPassword);

}
