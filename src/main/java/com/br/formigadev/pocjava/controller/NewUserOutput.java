package com.br.formigadev.pocjava.controller;

import com.br.formigadev.pocjava.controller.dto.NewUserResponse;
import com.br.formigadev.pocjava.entities.User;

public interface NewUserOutput {

    NewUserResponse toResponse(User u);
}
