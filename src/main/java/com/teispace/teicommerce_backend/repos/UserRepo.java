package com.teispace.teicommerce_backend.repos;

import com.teispace.teicommerce_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByUserNameOrEmail(String userName, String email);

    Optional<User> findUserByEmail(String email);

    Boolean existsUserByEmail(String email);

    Boolean existsUsersByUserName(String username);

    Boolean existsUsersByPhoneNumber(String phoneNumber);
}
