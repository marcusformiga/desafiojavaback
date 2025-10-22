package com.br.formigadev.pocjava.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Documentacao API",
                version = "v1.0",
                description = "Documentação da API.",
                contact = @Contact(
                        name = "marcus formiga",
                        email = "marcus.formiga15@example.com"
                )
        ),
        servers = {
                @Server(url = "/", description = "Servidor Local (Acesso Direto)")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Token JWT de Autorização. Use o token recebido no endpoint /api/auth/login."
)
public class OpenApiConfig {
}
