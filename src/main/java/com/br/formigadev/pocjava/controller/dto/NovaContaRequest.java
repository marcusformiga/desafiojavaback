package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.entities.Conta;
import com.br.formigadev.pocjava.entities.User;
import com.br.formigadev.pocjava.repository.UserRepository;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record NovaContaRequest(@NotBlank String agencia, @NotBlank String numero, @NotNull @DecimalMin(value = "1.00") BigDecimal saldo, UUID donoId) {


    public Conta toEntity(UserRepository userRepository){
        User dono = userRepository.getReferenceById(donoId);

        return new Conta(this.agencia, this.numero, this.saldo, dono);
    }
}
