package com.br.formigadev.pocjava.controller.cobrancas;

import com.br.formigadev.pocjava.entities.Cobranca;
import com.br.formigadev.pocjava.entities.CobrancaStatus;
import com.br.formigadev.pocjava.entities.StatusPagamento;
import com.br.formigadev.pocjava.entities.TipoPagamento;

import java.math.BigDecimal;

public record CancelarCobrancaRequest(BigDecimal valor, StatusPagamento statusPagamento, CobrancaStatus statusCobranca, TipoPagamento tipoPagamento) {

    Cobranca toEntity(){
        return new Cobranca();
    }
}
