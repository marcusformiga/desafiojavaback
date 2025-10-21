package com.br.formigadev.pocjava.repository;

import com.br.formigadev.pocjava.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository <Conta, Long> {


}
