package com.br.formigadev.pocjava.controller;

import com.br.formigadev.pocjava.controller.dto.NovoUsuarioRequest;
import com.br.formigadev.pocjava.controller.dto.NovoUsuarioResponse;
import com.br.formigadev.pocjava.usecases.CreateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api")
@RestController
public class CreateUserController {

    private final CreateUserUseCase createUserUseCase;

    public CreateUserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<NovoUsuarioResponse> register(@RequestBody @Valid NovoUsuarioRequest request) {
        NovoUsuarioResponse newUser = createUserUseCase.execute(request);

        return ResponseEntity.ok(newUser);
    }
}
