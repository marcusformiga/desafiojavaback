package com.br.formigadev.pocjava.controller.usuario;

import com.br.formigadev.pocjava.controller.dto.ErrorResponse;
import com.br.formigadev.pocjava.controller.dto.LoginResponse;
import com.br.formigadev.pocjava.controller.dto.NovoUsuarioRequest;
import com.br.formigadev.pocjava.controller.dto.NovoUsuarioResponse;
import com.br.formigadev.pocjava.usecases.CreateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api")
@RestController
@Tag(name = "Criação de usuários", description = "APIs para criação de usuários")
public class CreateUserController {

    private final CreateUserUseCase createUserUseCase;

    public CreateUserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Operation(
            summary = "Criação de usuários",
            description = "Cria um usúario para fazer as operações no sistema.",
            tags = { "(Criação)" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Criação de usuario bem-sucedida.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = NovoUsuarioResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "token jwt inválido ou expirado.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Requisição inválida (dados de entrada no formato incorreto).",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/auth/registrar")
    public ResponseEntity<NovoUsuarioResponse> register(@RequestBody @Valid NovoUsuarioRequest request) {
        NovoUsuarioResponse newUser = createUserUseCase.execute(request);

        return ResponseEntity.ok(newUser);
    }
}
