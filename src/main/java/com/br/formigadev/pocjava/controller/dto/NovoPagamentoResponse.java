package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.entities.Pagamento;
import com.br.formigadev.pocjava.entities.StatusPagamento;

import java.time.LocalDate;
import java.util.UUID;

public record NovoPagamentoResponse(Long pagamentoId, Long cobrancaId, UUID pagadorId, LocalDate dataPagamento, StatusPagamento statusPagamento, Integer parcelas) {

    public static NovoPagamentoResponse toResponse(Pagamento pagamento) {
        NovoPagamentoResponse response = new NovoPagamentoResponse(pagamento.getId(), pagamento.getCobranca().getCobrancaId(), pagamento.getPagador().getUserId(), pagamento.getDataPagamento(), pagamento.getStatus(), pagamento.getNumeroParcelas());

        return response;
    }
}
