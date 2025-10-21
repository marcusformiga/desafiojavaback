package com.br.formigadev.pocjava.usecases;

import com.br.formigadev.pocjava.controller.dto.NewBillRequest;
import com.br.formigadev.pocjava.controller.dto.NovaCobrancaResponse;
import com.br.formigadev.pocjava.entities.Cobranca;
import com.br.formigadev.pocjava.entities.User;
import com.br.formigadev.pocjava.repository.CobrancaRepository;
import com.br.formigadev.pocjava.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GerarCobrancaUseCase {

    private final UserRepository userRepository;
    private final CobrancaRepository cobrancaRepository;


    public GerarCobrancaUseCase(UserRepository userRepository, CobrancaRepository cobrancaRepository) {
        this.userRepository = userRepository;
        this.cobrancaRepository = cobrancaRepository;
    }

    @Transactional
    public NovaCobrancaResponse execute(NewBillRequest request){
        Cobranca cobranca = request.toEntity();
        User destinatario = userRepository.findByCpf(request.cpfDestinatario()).orElseThrow(() -> new RuntimeException("Usuario com o cpf informado não encontrado"));
        if (destinatario.getCpf().equals(request.cpfOrigem())){
            throw new RuntimeException("Cpf do destinatario deve ser diferente da origem");
        }
        User origem = userRepository.findByCpf(request.cpfOrigem()).orElseThrow(() -> new RuntimeException("Usuario com o cpf informado não encontrado"));
        if (request.isMenorQueZero()) {
            throw new RuntimeException("Não é possivel gerar uma cobrança de valor negativo");
        }
        cobranca.setDestinatario(destinatario);
        cobranca.setOrigem(origem);
        Cobranca cobrancaCriada = cobrancaRepository.save(cobranca);
        return new NovaCobrancaResponse(cobrancaCriada.getCobrancaId(), cobrancaCriada.getValor(), cobrancaCriada.getDescricao(), cobrancaCriada.getStatus());

    }


}
