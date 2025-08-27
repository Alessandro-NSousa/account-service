package com.account.service.dto;

import com.account.service.enumeration.UserRole;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String nome,
        String email,
        UserRole role,
        Boolean ativo
) {
}
