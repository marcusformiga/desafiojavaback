package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.entities.User;

public record NovoUsuarioResponse(String userId, String email, String name)  {

    public NovoUsuarioResponse toResponse(User user) {
        return new NovoUsuarioResponse(user.getUserId().toString(), user.getEmail(), user.getName());
    }
}
