package com.br.formigadev.pocjava.controller.dto;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record LoginRequest(@CPF(message = "cpf inválido") String cpf, String password, @Email(message = "email inválido") String email) {

}
