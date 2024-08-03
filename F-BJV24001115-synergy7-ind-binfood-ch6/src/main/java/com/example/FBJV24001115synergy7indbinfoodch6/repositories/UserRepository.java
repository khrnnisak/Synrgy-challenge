package com.example.FBJV24001115synergy7indbinfoodch6.repositories;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

    @Procedure(procedureName = "user_insert")
    void userInsertData(String email, String username, String password);

    @Procedure(procedureName = "user_update")
    void userUpdateData(UUID id, String newPassword);

    @Procedure(procedureName = "user_delete")
    void userDeleteData(UUID id);

    User findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmailAddress(String emailAddress);

    @Query(value = "select * from users where email_address =?1 and username =?2 and deleted_date is null", nativeQuery = true)
    Optional<User> findByEmailandUsername(String emailAddress, String username);
}
