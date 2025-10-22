package com.br.formigadev.pocjava.usecases;


import com.br.formigadev.pocjava.entities.CobrancaStatus;
import com.br.formigadev.pocjava.entities.Cobranca;
import com.br.formigadev.pocjava.entities.User;
import com.br.formigadev.pocjava.repository.CobrancaRepository;
import com.br.formigadev.pocjava.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ConsultarCobrancasUseCase {

    private final UserRepository userRepository;
    private final CobrancaRepository cobrancaRepository;

    public ConsultarCobrancasUseCase(UserRepository userRepository, CobrancaRepository cobrancaRepository) {
        this.userRepository = userRepository;
        this.cobrancaRepository = cobrancaRepository;
    }

    public HashSet<Cobranca> consultarCobrancasEnviadas(String cpfOriginador, CobrancaStatus status) {
        User originador = userRepository.findByCpf(cpfOriginador)
                .orElseThrow(() -> new RuntimeException("Usuário originador não encontrado"));
        return cobrancaRepository.findByOrigemAndStatus(originador, status);
    }

    public List<Cobranca> consultarCobrancasRecebidas(String cpfDestinatario, CobrancaStatus status) {
        User destinatario = userRepository.findByCpf(cpfDestinatario)
                .orElseThrow(() -> new RuntimeException("Usuário destinatário não encontrado"));
        return cobrancaRepository.findByDestinatarioAndStatus(destinatario, status);
    }
}

