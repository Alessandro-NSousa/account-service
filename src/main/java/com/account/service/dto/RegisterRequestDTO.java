package com.account.service.dto;

import com.account.service.enumeration.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record RegisterRequestDTO(
        @NotBlank(message = "O Campo Nome é obrigatório!")
        String nome,
        @Email @NotBlank(message = "O Campo E-mail é obrigatório!")
        String email,
        @Size(min = 5, message = "A Senha deve connter no mínimo 5 caracteres")
        String password,
        UserRole role

) {
}
