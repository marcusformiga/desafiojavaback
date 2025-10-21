package com.br.formigadev.pocjava.controller;


import com.br.formigadev.pocjava.controller.dto.NovoPagamentoRequest;
import com.br.formigadev.pocjava.controller.dto.NovoPagamentoResponse;
import com.br.formigadev.pocjava.usecases.FazerPagamentoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos")
public class NovoPagamentoController {

    private final FazerPagamentoUseCase pagamentoUseCase;

    public NovoPagamentoController(FazerPagamentoUseCase pagamentoUseCase) {
        this.pagamentoUseCase = pagamentoUseCase;
    }


    @PostMapping
    public ResponseEntity<?> realizarPagamento(@RequestBody NovoPagamentoRequest request) {
        try {
            NovoPagamentoResponse pagamento = pagamentoUseCase.processarPagamento(request);
            return ResponseEntity.ok(pagamento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar pagamento: " + e.getMessage());
        }
    }
}

