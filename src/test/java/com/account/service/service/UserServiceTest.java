package com.account.service.service;

import com.account.service.builder.UserDTOBuilder;
import com.account.service.domain.User;
import com.account.service.dto.LoginRequestDTO;
import com.account.service.dto.LoginResponseDTO;
import com.account.service.dto.RegisterRequestDTO;
import com.account.service.dto.RegisterResponseDTO;
import com.account.service.infra.security.TokenService;
import com.account.service.mapper.UserMapper;
import com.account.service.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
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

    @Test
    void whenValidCredentials_thenReturnLoginResponse() {
        // Arrange
        String email = "usuario@exemplo.com";
        String nome = "testeNome";
        String senha = "senha123";
        String fakeToken = "jwt-token-123";

        LoginRequestDTO request = new LoginRequestDTO(email, senha);

        User fakeUser = new User();
        fakeUser.setNome(nome);
        fakeUser.setEmail(email);
        fakeUser.setPassword(senha);

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(fakeUser);
        when(tokenService.generateToken(fakeUser)).thenReturn(fakeToken);

        // Act
        LoginResponseDTO response = userService.login(request);

        // Assert
        assertThat(response.nome(), is(nome));
        assertThat(response.token(), is(fakeToken));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(fakeUser);
    }

    @Test
    void WhenTheAuthenticationDataIsEnteredThenTheLoginMustBeDone() {
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