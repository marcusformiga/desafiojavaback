package com.br.formigadev.pocjava.controller;

import com.br.formigadev.pocjava.client.AutorizadorClient;
import com.br.formigadev.pocjava.client.AutorizadorResponse;
import com.br.formigadev.pocjava.controller.dto.NovoDepositoRequest;
import com.br.formigadev.pocjava.entities.Conta;
import com.br.formigadev.pocjava.repository.ContaRepository;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/contas")
public class GerarDepositoController {

    private final ContaRepository contaRepository;
    private final AutorizadorClient autorizadorClient;

    public GerarDepositoController(ContaRepository contaRepository, AutorizadorClient autorizadorClient) {
        this.contaRepository = contaRepository;
        this.autorizadorClient = autorizadorClient;
    }

    @PostMapping("/depositar")
    @Transactional
    public ResponseEntity<?> realizarDeposito(@RequestBody @Valid NovoDepositoRequest request) {
        try {
            Conta conta = contaRepository.findById(request.contaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada."));

            AutorizadorResponse autorizacao = autorizadorClient.autorizar();

            if (!autorizacao.data().authorized()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Depósito não autorizado pelo sistema externo.");
            }

            conta.creditar(request.valor());
            contaRepository.save(conta);

            return ResponseEntity.ok().body("Deposito realizado com sucesso");

        } catch (FeignException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Erro ao comunicar com o serviço de autorização.",
                    e
            );
        }
    }
}

