package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.entities.CobrancaStatus;
import com.br.formigadev.pocjava.entities.Cobranca;

import java.math.BigDecimal;

public record NovaCobrancaResponse(Long cobrancaId, BigDecimal valor, String descricao, CobrancaStatus status) {

    public static NovaCobrancaResponse fromEntity(Cobranca cobranca) {
        return new NovaCobrancaResponse(cobranca.getCobrancaId(), cobranca.getValor(), cobranca.getDescricao(), cobranca.getStatus());
    }

}

