package com.br.formigadev.pocjava.usecases;

import com.br.formigadev.pocjava.entities.Cobranca;
import com.br.formigadev.pocjava.entities.CobrancaStatus;
import com.br.formigadev.pocjava.entities.User;
import com.br.formigadev.pocjava.entities.vo.PlainPassword;
import com.br.formigadev.pocjava.repository.CobrancaRepository;
import com.br.formigadev.pocjava.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarCobrancasUseCaseTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CobrancaRepository cobrancaRepository;

    @InjectMocks
    ConsultarCobrancasUseCase consultarCobrancasUseCase;

    User user;
    Cobranca cobranca;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", new PlainPassword("passwordsecreto"), "12345678900", "john@doe.com");
        cobranca = new Cobranca("12345678900", new BigDecimal("100.00"), "Teste cobrança", "09876543211");
    }

    @Test
    @DisplayName("Deve retornar cobranças enviadas quando o originador existe")
    void deveRetornarCobrancasEnviadas() {
        HashSet<Cobranca> cobrancas = new HashSet<>(Set.of(cobranca));
        when(userRepository.findByCpf("12345678900")).thenReturn(Optional.of(user));
        when(cobrancaRepository.findByOrigemAndStatus(user, CobrancaStatus.PENDENTE)).thenReturn(cobrancas);

        Set<Cobranca> resultado = consultarCobrancasUseCase.consultarCobrancasEnviadas("12345678900", CobrancaStatus.PENDENTE);

        assertEquals(1, resultado.size());
        verify(cobrancaRepository).findByOrigemAndStatus(user, CobrancaStatus.PENDENTE);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o originador não for encontrado")
    void deveLancarExcecaoQuandoOriginadorNaoEncontrado() {
        when(userRepository.findByCpf("12345678900")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> consultarCobrancasUseCase.consultarCobrancasEnviadas("12345678900", CobrancaStatus.PENDENTE)
        );

        assertEquals("Usuário originador não encontrado", ex.getMessage());
        verify(cobrancaRepository, never()).findByOrigemAndStatus(any(), any());
    }

    @Test
    @DisplayName("Deve retornar cobranças recebidas quando o destinatário existe")
    void deveRetornarCobrancasRecebidas() {
        List<Cobranca> cobrancas = List.of(cobranca);
        when(userRepository.findByCpf("09876543211")).thenReturn(Optional.of(user));
        when(cobrancaRepository.findByDestinatarioAndStatus(user, CobrancaStatus.PENDENTE)).thenReturn(cobrancas);

        List<Cobranca> resultado = consultarCobrancasUseCase.consultarCobrancasRecebidas("09876543211", CobrancaStatus.PENDENTE);

        assertEquals(1, resultado.size());
        verify(cobrancaRepository).findByDestinatarioAndStatus(user, CobrancaStatus.PENDENTE);
    }
    @Test
    @DisplayName("Deve retornar cobranças recebidas quando o destinatário existe e ja foram pagas")
    void deveRetornarCobrancasRecebidasPagas() {
        List<Cobranca> cobrancas = List.of(cobranca);
        when(userRepository.findByCpf("09876543211")).thenReturn(Optional.of(user));
        when(cobrancaRepository.findByDestinatarioAndStatus(user, CobrancaStatus.PAGA)).thenReturn(cobrancas);

        List<Cobranca> resultado = consultarCobrancasUseCase.consultarCobrancasRecebidas("09876543211", CobrancaStatus.PAGA);

        assertEquals(1, resultado.size());
        verify(cobrancaRepository).findByDestinatarioAndStatus(user, CobrancaStatus.PAGA);
    }

}