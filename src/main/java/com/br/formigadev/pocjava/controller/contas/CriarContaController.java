package com.br.formigadev.pocjava.controller.contas;

import com.br.formigadev.pocjava.controller.dto.NovaContaRequest;
import com.br.formigadev.pocjava.controller.dto.NovaContaResponse;
import com.br.formigadev.pocjava.entities.Conta;
import com.br.formigadev.pocjava.repository.ContaRepository;
import com.br.formigadev.pocjava.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api")
@RestController
public class CriarContaController {

    private final ContaRepository repository;
    private final UserRepository userRepository;

    public CriarContaController(ContaRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }


    @PostMapping(value = "/contas")
    @Transactional
    public ResponseEntity<NovaContaResponse> criarConta(@RequestBody @Valid NovaContaRequest request){
        Conta entity = request.toEntity(userRepository);
        Conta contaSalva = repository.save(entity);
        NovaContaResponse response = new NovaContaResponse(contaSalva.getId(), contaSalva.getAgencia(), contaSalva.getNumero(), contaSalva.getId());

        return ResponseEntity.ok(response);

    }
}
