package com.example.FBJV24001115synergy7indbinfoodch6.models.accounts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@Table(name = "users")
@SQLDelete(sql = "update users set deleted_date = now() where id =?")
@SQLRestriction("deleted_date is null")
public class User extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;
    private String emailAddress;
    private String password;
    private boolean verified = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
