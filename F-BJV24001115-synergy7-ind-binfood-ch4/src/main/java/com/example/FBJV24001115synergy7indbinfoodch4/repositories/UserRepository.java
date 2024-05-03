package com.example.FBJV24001115synergy7indbinfoodch4.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch4.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

    @Procedure(procedureName = "user_insert")
    void userInsertData(String email, String username, String password);

    @Procedure(procedureName = "user_update")
    void userUpdateData(UUID id, String newPassword);

    @Procedure(procedureName = "user_delete")
    void userDeleteData(UUID id);

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
