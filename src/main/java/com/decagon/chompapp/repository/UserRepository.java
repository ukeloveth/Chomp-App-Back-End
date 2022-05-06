package com.decagon.chompapp.repository;

import com.decagon.chompapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByConfirmationToken (String token);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
