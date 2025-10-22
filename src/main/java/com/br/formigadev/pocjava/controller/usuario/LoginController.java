package com.br.formigadev.pocjava.controller.usuario;


import com.br.formigadev.pocjava.config.TokenConfig;
import com.br.formigadev.pocjava.controller.dto.ErrorResponse;
import com.br.formigadev.pocjava.controller.dto.LoginRequest;
import com.br.formigadev.pocjava.controller.dto.LoginResponse;
import com.br.formigadev.pocjava.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Autenticação (Login)", description = "APIs para login de usuário e geração de tokens de acesso.")
public class LoginController {


    private final TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;

    public LoginController(TokenConfig tokenConfig, AuthenticationManager authenticationManager) {
        this.tokenConfig = tokenConfig;
        this.authenticationManager = authenticationManager;
    }


    @Operation(
            summary = "Realiza o login e gera um token JWT",
            description = "Autentica o usuário com e-mail e senha e retorna um token de acesso válido para ser usado em endpoints protegidos.",
            tags = { "Autenticação (Login)" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Login bem-sucedido. Retorna o token de acesso e a expiração.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Credenciais inválidas (e-mail ou senha incorretos).",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Requisição inválida (e-mail ou senha faltando ou com formato incorreto).",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> getToken(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);
        User user = (User) authentication.getPrincipal();
        var token = tokenConfig.encodeToken(user);
        var response = new LoginResponse(token.accessToken(), token.expirationTime());
        return ResponseEntity.ok(response);
    }


}

