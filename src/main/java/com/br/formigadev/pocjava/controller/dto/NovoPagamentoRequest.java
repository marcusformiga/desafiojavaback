package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.client.CartaoData;
import com.br.formigadev.pocjava.entities.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record NovoPagamentoRequest(
        UUID pagadorId,
        Long cobrancaId,
        TipoPagamento tipoPagamento,
        Integer numeroParcelas,
        @Future
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)LocalDate diaPagamento,
        BigDecimal valorPago
) {}
