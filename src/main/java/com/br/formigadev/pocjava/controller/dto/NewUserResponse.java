package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.controller.NewUserOutput;
import com.br.formigadev.pocjava.entities.User;

public record NewUserResponse(String userId, String email, String name) implements NewUserOutput {


    @Override
    public NewUserResponse toResponse(User user) {
        return new NewUserResponse(user.getUserId().toString(), user.getEmail(), user.getName());
    }
}
