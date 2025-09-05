package com.account.service.repository;

import com.account.service.domain.User;
import com.account.service.dto.RegisterRequestDTO;
import com.account.service.enumeration.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("caso de sucesso - encontrar usuário por email")
    void findByEmailCaso1() {
        String email = "allessandro188@gmail.com";
        RegisterRequestDTO data = new RegisterRequestDTO("Alessandro",
                email,
                "951935",
                UserRole.ADMIN);
        this.createUser(data);

        UserDetails result = this.userRepository.findByEmail(email);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("caso de sucesso - não encontrar o usuário por email")
    void findByEmailCaso2() {
        String email = "allessandro188@gmail.com";

        UserDetails result = this.userRepository.findByEmail(email);

        assertThat(result).isNull();
    }

    private User createUser(RegisterRequestDTO data) {
        User user = new User(data, null);
        this.entityManager.persist(user);
        return user;
    }

}