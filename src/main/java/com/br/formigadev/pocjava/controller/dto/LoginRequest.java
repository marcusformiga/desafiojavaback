package com.br.formigadev.pocjava.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record LoginRequest(@Schema(description = "Cpf do usuário", example = "109.446.720-00", type = "string") @CPF(message = "cpf inválido") String cpf, @Schema(description = "Senha do usuário", example = "senhasecreata", type = "string") String password, @Email(message = "email inválido") @Schema(description = "Endereço de e-mail do usuário", example = "john.doe@example.com", type = "string") String email) {

}
