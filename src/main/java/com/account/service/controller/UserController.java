package com.account.service.controller;

import com.account.service.domain.User;
import com.account.service.dto.*;
import com.account.service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("v1/api/auth-service")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO registerRequest, UriComponentsBuilder uriBuilder){
        try {
            var newRegister = userService.register(registerRequest);
            var uri = uriBuilder.path("v1/api/auth-service/{id}").buildAndExpand(newRegister.id()).toUri();

            return ResponseEntity.created(uri).body(newRegister);
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErroResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO body) {

        LoginResponseDTO response = userService.login(body);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable UUID id) {
        var user = userService.detailUser(id);

        return ResponseEntity.ok(user);
    }
}
