package com.example.FBJV24001115synergy7indbinfoodch6.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otp")
@SQLDelete(sql = "update otp set deleted_date = now() where id =?")
@SQLRestriction("deleted_date is null")
public class OTP extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String emailAddress;
    private String otp;
    private LocalDateTime expiredTime;

}
