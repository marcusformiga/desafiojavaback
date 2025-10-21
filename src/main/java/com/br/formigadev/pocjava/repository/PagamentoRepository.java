package com.br.formigadev.pocjava.repository;

import com.br.formigadev.pocjava.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
