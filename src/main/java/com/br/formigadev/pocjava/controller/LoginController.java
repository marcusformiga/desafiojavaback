package com.br.formigadev.pocjava.controller;


import com.br.formigadev.pocjava.config.TokenConfig;
import com.br.formigadev.pocjava.controller.dto.LoginRequest;
import com.br.formigadev.pocjava.controller.dto.LoginResponse;
import com.br.formigadev.pocjava.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável por endpoints de autenticação, como geração de tokens JWT.
 */
@RestController
@RequestMapping("api/auth")
public class LoginController {


    private final TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;

    public LoginController(TokenConfig tokenConfig, AuthenticationManager authenticationManager) {
        this.tokenConfig = tokenConfig;
        this.authenticationManager = authenticationManager;
    }


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

