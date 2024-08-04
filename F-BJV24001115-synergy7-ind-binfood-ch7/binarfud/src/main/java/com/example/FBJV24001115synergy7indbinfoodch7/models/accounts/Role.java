package com.example.FBJV24001115synergy7indbinfoodch7.models.accounts;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.FBJV24001115synergy7indbinfoodch7.models.BaseModel;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Order;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Enumerated(EnumType.STRING)
    private ERole name;
    public enum ERole{
        ROLE_USER,
        ROLE_MERCHANT
    }
}
