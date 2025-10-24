package com.account.service.repository;

import com.account.service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);

    @Query("""
        select u
        from User u
        where 
        u.email = :email
        """)
    Optional<User> findEmail(String email);
}
