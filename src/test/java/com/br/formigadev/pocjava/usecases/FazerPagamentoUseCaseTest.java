package com.br.formigadev.pocjava.usecases;

import com.br.formigadev.pocjava.controller.dto.NovaCobrancaRequest;
import com.br.formigadev.pocjava.controller.dto.NovoPagamentoRequest;
import com.br.formigadev.pocjava.controller.dto.NovoPagamentoResponse;
import com.br.formigadev.pocjava.controller.dto.NovoUsuarioRequest;
import com.br.formigadev.pocjava.entities.*;
import com.br.formigadev.pocjava.repository.CobrancaRepository;
import com.br.formigadev.pocjava.repository.ContaRepository;
import com.br.formigadev.pocjava.repository.PagamentoRepository;
import com.br.formigadev.pocjava.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FazerPagamentoUseCaseTest {

    @Mock
    PagamentoRepository pagamentoRepository;
    @Mock
    CobrancaRepository cobrancaRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ContaRepository contaRepository;
    @InjectMocks
    FazerPagamentoUseCase useCase;

    @Test
    @DisplayName("Fazer pagamento usuario nao encontrado")
    void testeFazerPagamentoUsuairoNaoExiste(){
        // ARrange
        var pagadorID = UUID.randomUUID();
        var cobrancaId = 1L;
        NovoPagamentoRequest request = new NovoPagamentoRequest(pagadorID, cobrancaId, TipoPagamento.AVISTA, 1, LocalDate.now(), new BigDecimal("109"));
        // ACT
        when(userRepository.findById(pagadorID)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->useCase.processarPagamento(request));
        // assert
        assertEquals("Usuário pagador não encontrado", exception.getMessage());
        verify(pagamentoRepository, never()).save(any(Pagamento.class));
    }
    @Test
    @DisplayName("Fazer pagamento cobrança nao encontrada")
    void testeFazerPagamentoCobrancaNaoExiste(){
        // ARrange
        var pagadorID = UUID.randomUUID();
        var cobrancaId = 1L;
        NovoUsuarioRequest request = new NovoUsuarioRequest("nome", "109.446.720-00", "12345678900", "johndoe@gmail.com");
        User userEntity = request.toEntity();
        NovoPagamentoRequest pagamentoRequest = new NovoPagamentoRequest(pagadorID, cobrancaId, TipoPagamento.AVISTA, 1, LocalDate.now(), new BigDecimal("109"));
        // ACT
        when(userRepository.findById(pagadorID)).thenReturn(Optional.of(userEntity));
        when(cobrancaRepository.findById(cobrancaId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->useCase.processarPagamento(pagamentoRequest));
        // assert
        assertEquals("Cobrança nao encontrada", exception.getMessage());
        verify(pagamentoRepository, never()).save(any(Pagamento.class));
    }
    @Test
    @DisplayName("Fazer pagamento com cobranca com status ='PAGA' ")
    void testeFazerPagamentoCobrancaJaPaga(){
        // ARrange
        var pagadorID = UUID.randomUUID();
        var cobrancaId = 1L;
        NovoUsuarioRequest request = new NovoUsuarioRequest("nome", "109.446.720-00", "12345678900", "johndoe@gmail.com");
        NovaCobrancaRequest novaCobrancaRequest = new NovaCobrancaRequest("109.446.720-00", new BigDecimal("100.00"), "cobranca paga", "958.269.480-78");
        User userEntity = request.toEntity();
        Cobranca cobranca = novaCobrancaRequest.toEntity();
        cobranca.setStatus(CobrancaStatus.PAGA);
        NovoPagamentoRequest pagamentoRequest = new NovoPagamentoRequest(pagadorID, cobrancaId, TipoPagamento.AVISTA, 1, LocalDate.now(), new BigDecimal("109"));
        // ACT
        when(userRepository.findById(pagadorID)).thenReturn(Optional.of(userEntity));
        when(cobrancaRepository.findById(cobrancaId)).thenReturn(Optional.of(cobranca));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->useCase.processarPagamento(pagamentoRequest));
        // assert
        assertEquals("Cobrança já paga ou inválida", exception.getMessage());
        verify(pagamentoRepository, never()).save(any(Pagamento.class));
    }

}