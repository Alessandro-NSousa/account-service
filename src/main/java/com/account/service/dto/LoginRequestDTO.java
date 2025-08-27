package com.account.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @Email
        String email,
        String password) {
}
