package com.example.FBJV24001115synergy7indbinfoodch6.models.accounts;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.FBJV24001115synergy7indbinfoodch6.models.BaseModel;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Order;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@SQLDelete(sql = "update users set deleted_date = now() where id =?")
@SQLRestriction("deleted_date is null")
public class Role extends BaseModel{
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
