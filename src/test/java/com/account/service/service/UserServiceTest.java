package com.account.service.service;

import com.account.service.builder.UserDTOBuilder;
import com.account.service.domain.User;
import com.account.service.dto.RegisterRequestDTO;
import com.account.service.dto.RegisterResponseDTO;
import com.account.service.mapper.UserMapper;
import com.account.service.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void WhenTheUserIsInformedThenItMustBeCreated() {
        var builder = UserDTOBuilder.builder().build();
        User user = builder.toUser();
        RegisterRequestDTO request = builder.buildRegisterRequestDTO();
        RegisterResponseDTO responseDTO = new RegisterResponseDTO(user.getId(), user.getEmail(), user.getRole());

        // Mocks
        when(userRepository.findByEmail(request.email())).thenReturn(null);
        when(passwordEncoder.encode(request.password())).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(mapper.UserToRegisterResponseDTO(any(User.class))).thenReturn(responseDTO);

        // Act
        var response = userService.register(request);

        // Assert
        assertThat(response.username(), Matchers.is(request.email()));
        assertThat(response.role(), Matchers.is(request.role()));
    }
}