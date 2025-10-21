package com.br.formigadev.pocjava.repository;

import com.br.formigadev.pocjava.entities.CobrancaStatus;
import com.br.formigadev.pocjava.entities.Cobranca;
import com.br.formigadev.pocjava.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;

public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {

    HashSet<Cobranca> findByOrigemAndStatus(User originador, CobrancaStatus status);
    List<Cobranca> findByDestinatarioAndStatus(User destinatario, CobrancaStatus status);
}
