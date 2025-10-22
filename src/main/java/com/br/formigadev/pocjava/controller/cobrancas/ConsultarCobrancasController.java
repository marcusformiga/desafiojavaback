package com.br.formigadev.pocjava.controller.cobrancas;

import com.br.formigadev.pocjava.controller.dto.NovaCobrancaResponse;
import com.br.formigadev.pocjava.entities.CobrancaStatus;
import com.br.formigadev.pocjava.entities.Cobranca;
import com.br.formigadev.pocjava.usecases.ConsultarCobrancasUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cobrancas")
public class ConsultarCobrancasController {

    private final ConsultarCobrancasUseCase useCase;

    public ConsultarCobrancasController(ConsultarCobrancasUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/enviadas")
    public ResponseEntity<Set<NovaCobrancaResponse>> listarEnviadas(@RequestParam String cpfOriginador, @RequestParam CobrancaStatus status) {
        HashSet<Cobranca> cobrancas = useCase.consultarCobrancasEnviadas(cpfOriginador, status);
        if (cobrancas.isEmpty()){
            return ResponseEntity.ok(new HashSet<>());
        }
        Set<NovaCobrancaResponse> cobrancaResponses = cobrancas.stream().map(NovaCobrancaResponse::fromEntity).collect(Collectors.toUnmodifiableSet());
        return ResponseEntity.ok(cobrancaResponses);
    }

    @GetMapping("/recebidas")
    public List<NovaCobrancaResponse> listarRecebidas(@RequestParam String cpfDestinatario, @RequestParam CobrancaStatus status) {
        return useCase.consultarCobrancasRecebidas(cpfDestinatario, status)
                .stream()
                .map(NovaCobrancaResponse::fromEntity)
                .toList();
    }
}
