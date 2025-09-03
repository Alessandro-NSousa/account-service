package com.account.service.builder;

import com.account.service.domain.User;
import com.account.service.enumeration.UserRole;
import lombok.Builder;

import java.util.UUID;

@Builder
public class UserDTOBuilder {
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Builder.Default
    private String nome = "Usuário Padrão";

    @Builder.Default
    private String email = "usuario@exemplo.com";

    @Builder.Default
    private String password = "senha123";

    @Builder.Default
    private UserRole role = UserRole.USER;

    @Builder.Default
    private Boolean ativo = true;

    public User toUser() {
        User user = new User(nome, email, password, role);
        user.setId(id);
        user.setAtivo(ativo);
        return user;
    }
}
