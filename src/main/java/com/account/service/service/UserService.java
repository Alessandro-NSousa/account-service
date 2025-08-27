package com.account.service.service;

import com.account.service.domain.User;
import com.account.service.dto.LoginRequestDTO;
import com.account.service.dto.LoginResponseDTO;
import com.account.service.dto.RegisterRequestDTO;
import com.account.service.dto.UserResponseDTO;
import com.account.service.infra.security.TokenService;
import com.account.service.mapper.UserMapper;
import com.account.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequestDTO dados){

        if(this.userRepository.findByEmail(dados.email()) != null) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(dados.password());
        User newUser = new User(dados, encryptedPassword);

        this.userRepository.save(newUser);

        return newUser;
    }

    public LoginResponseDTO login(LoginRequestDTO body) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDTO(auth.getName(), token);
    }

    public UserResponseDTO detailUser(UUID id) {
        var usuario = this.userRepository.getReferenceById(id);
        return mapper.UserToUserResponseDTO(usuario);
    }
}
