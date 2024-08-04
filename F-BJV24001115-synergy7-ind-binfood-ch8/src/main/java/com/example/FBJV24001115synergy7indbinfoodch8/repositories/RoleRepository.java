package com.example.FBJV24001115synergy7indbinfoodch8.repositories;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch8.models.accounts.Role;
import com.example.FBJV24001115synergy7indbinfoodch8.models.accounts.Role.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
    Optional<Role> findByName(ERole name);
}
