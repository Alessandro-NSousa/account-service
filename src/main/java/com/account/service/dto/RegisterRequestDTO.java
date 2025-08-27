package com.account.service.dto;

import com.account.service.enumeration.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotNull
        String nome,
        @Email @NotNull
        String email,
        @Size(min = 5)
        String password,
        UserRole role
) {
}
