package com.br.formigadev.pocjava.controller.cobrancas;

import com.br.formigadev.pocjava.controller.dto.NovaCobrancaRequest;
import com.br.formigadev.pocjava.controller.dto.NovaCobrancaResponse;
import com.br.formigadev.pocjava.usecases.GerarCobrancaUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class GerarCobrancaController {

    private final GerarCobrancaUseCase useCase;

    public GerarCobrancaController(GerarCobrancaUseCase useCase) {
        this.useCase = useCase;
    }


    @PostMapping(value = "/cobrancas")
    public ResponseEntity<NovaCobrancaResponse> create(@RequestBody @Valid NovaCobrancaRequest billRequest){
        NovaCobrancaResponse response = useCase.execute(billRequest);
        return ResponseEntity.ok(response);
    }
}
