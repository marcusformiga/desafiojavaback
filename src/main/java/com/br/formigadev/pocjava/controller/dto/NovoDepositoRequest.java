package com.br.formigadev.pocjava.controller.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record NovoDepositoRequest(Long contaId, @DecimalMin(value = "1.00", message = "o valor depositado deve ser no minimo 1 real") BigDecimal valor) {
}
