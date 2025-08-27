package com.account.service.dto;

import com.account.service.enumeration.UserRole;

import java.util.UUID;

public record RegisterResponseDTO(UUID id,
                                  String username,
                                  UserRole role) {
}
