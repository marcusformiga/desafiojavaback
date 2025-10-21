package com.br.formigadev.pocjava.usecases;

import com.br.formigadev.pocjava.client.AutorizadorClient;
import com.br.formigadev.pocjava.controller.dto.NovoPagamentoRequest;
import com.br.formigadev.pocjava.controller.dto.NovoPagamentoResponse;
import com.br.formigadev.pocjava.entities.*;
import com.br.formigadev.pocjava.repository.CobrancaRepository;
import com.br.formigadev.pocjava.repository.ContaRepository;
import com.br.formigadev.pocjava.repository.PagamentoRepository;
import com.br.formigadev.pocjava.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FazerPagamentoUseCase {

    private final PagamentoRepository pagamentoRepository;
    private final CobrancaRepository cobrancaRepository;
    private final UserRepository userRepository;
    private final ContaRepository contaRepository;

    public FazerPagamentoUseCase(PagamentoRepository pagamentoRepository,
                                 CobrancaRepository cobrancaRepository,
                                 UserRepository userRepository,
                                 AutorizadorClient client, ContaRepository contaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.cobrancaRepository = cobrancaRepository;
        this.userRepository = userRepository;
        this.contaRepository = contaRepository;
    }

    @Transactional
    public NovoPagamentoResponse processarPagamento(NovoPagamentoRequest request) {
        // 1️⃣ Buscar o usuário pagador e as cobranças pendentes
        User pagador = userRepository.findById(request.pagadorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário pagador não encontrado"));

        Cobranca cobranca = cobrancaRepository.findById(request.cobrancaId()).orElseThrow(() -> new RuntimeException("Cobrança nao encontrada"));

        if (!cobranca.getStatus().equals(CobrancaStatus.PENDENTE)) {
            throw new IllegalStateException("Cobrança já paga ou inválida");
        }

        Pagamento pagamento = new Pagamento(cobranca, request.tipoPagamento(), pagador, cobranca.getValor(), request.diaPagamento());
        pagamento.setNumeroParcelas(request.numeroParcelas());

        if (request.tipoPagamento().getTipo().equals(TipoPagamento.AVISTA.getTipo())) {
            pagarPorSaldo(pagador, cobranca, pagamento);
            pagamento.setStatus(StatusPagamento.APROVADO);
        } else {
            throw new UnsupportedOperationException("Tipo de pagamento não suportado");
        }

        cobranca.setStatus(CobrancaStatus.PAGA);
        cobrancaRepository.save(cobranca);

        Pagamento pagamentoFeito = pagamentoRepository.save(pagamento);
        return new NovoPagamentoResponse(pagamentoFeito.getId(), cobranca.getCobrancaId(), pagador.getUserId(), request.diaPagamento(), pagamentoFeito.getStatus(), pagamentoFeito.getNumeroParcelas());

    }

    private void pagarPorSaldo(User pagador, Cobranca cobranca, Pagamento pagamento) {
        BigDecimal valor = cobranca.getValor();
        Conta contaPagador = pagador.getConta();
        Conta contaDestinatario = cobranca.getDestinatario().getConta();

        if (contaPagador == null || contaDestinatario == null) {
            throw new IllegalStateException("Conta bancária não encontrada para um dos usuários");
        }

        if (contaPagador.getSaldo().compareTo(valor) < 0){
            pagamento.setStatus(StatusPagamento.NEGADO);
            throw new RuntimeException("Saldo insuficiente para pagamento");
        }

        contaPagador.debitar(valor);
        contaDestinatario.creditar(valor);
        contaRepository.save(contaPagador);
        contaRepository.save(contaDestinatario);

        pagamento.setStatus(StatusPagamento.APROVADO);
    }

//    private void pagarPorCartao(Cobranca cobranca, Pagamento pagamento, NovoPagamentoRequest request) {
//        try {
//            AutorizadorResponse response = client.autorizar(request.cartaoData());
//
//            BigDecimal valorCobranca = cobranca.getValor();
//
//            if (response.data().authorized()) {
//                pagamento.setStatus(StatusPagamento.APROVADO);
//            } else {
//                pagamento.setStatus(StatusPagamento.NEGADO);
//            }
//        } catch (FeignException.FeignClientException e) {
//            pagamento.setStatus(StatusPagamento.FALHA);
//            throw new RuntimeException("Erro ao autorizar pagamento via cartão: " + e.getMessage(), e);
//        }
//    }
}
