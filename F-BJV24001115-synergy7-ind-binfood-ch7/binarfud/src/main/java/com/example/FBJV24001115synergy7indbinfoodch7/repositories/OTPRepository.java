package com.example.FBJV24001115synergy7indbinfoodch7.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch7.models.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, UUID>{
    Optional<OTP> findByEmailAddress(String email);

    @Query(value = "select * from otp where email_address =?1 and otp = ?2 and deleted_date is null", nativeQuery = true)
    Optional<OTP> findByEmailAddressandOtp(String email, String otp);

}
