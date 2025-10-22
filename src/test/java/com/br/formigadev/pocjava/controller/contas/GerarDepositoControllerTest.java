package com.br.formigadev.pocjava.controller.contas;

import com.br.formigadev.pocjava.client.AutorizadorClient;
import com.br.formigadev.pocjava.client.AutorizadorResponse;
import com.br.formigadev.pocjava.client.DataClient;
import com.br.formigadev.pocjava.controller.dto.NovoDepositoRequest;
import com.br.formigadev.pocjava.entities.Conta;
import com.br.formigadev.pocjava.entities.User;
import com.br.formigadev.pocjava.entities.vo.PlainPassword;
import com.br.formigadev.pocjava.repository.ContaRepository;
import com.br.formigadev.pocjava.repository.UserRepository;
import feign.FeignException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class GerarDepositoControllerMockMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContaRepository contaRepository;

    @MockBean
    AutorizadorClient autorizadorClient;

    @Autowired
    ObjectMapper objectMapper;

    Conta conta;

    User user;

    @BeforeEach
    void setup() {
        user = new User("john doe", new PlainPassword("passwordsecreto"), "109.446.720-00", "johndoe@gmail.com");
        userRepository.save(user);
        conta = new Conta("agencia", "numero", BigDecimal.TEN, user);
        contaRepository.save(conta);

    }

    @AfterEach
    void  config(){
        contaRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testRealizarDepositoSucesso() throws Exception {
        // Arrange
        var autorizadorResponse = new AutorizadorResponse("sucess", new DataClient(true));
        when(autorizadorClient.autorizar()).thenReturn(autorizadorResponse );

        NovoDepositoRequest request = new NovoDepositoRequest(conta.getId(), BigDecimal.valueOf(50));

        mockMvc.perform(post("/api/contas/depositar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLXNlcnZlciIsInN1YiI6Imtpa28gZm9ybWlnYSIsImlhdCI6MTc2MTE1ODc3MywiZXhwIjoxNzYxMTc2NzczLCJqdGkiOiIyOTY1ZTU2Ny0zNDE2LTQ1ZDQtODgwYy0zM2U3NGZlZjg1N2MiLCJ1c2VySWQiOiIxMDIzNTk0Yi1mZmI1LTQ5ZjMtYWRhYy1kYmEwYmRmMjhiZjgifQ.kiotGrYqb8sY3zVO7X80UymGpqKZR0r71227yuThD9h26cwvTN8lmLjCZ8c5ZpJvj-MugyH-3V8UhzSmXiysPOKmtC-Q-5v0tDEWKYQ00pMlqGRhG8GlFU6tpTIwcSW_VK3h26HAZJnBiYFKfE_FLt9KG6ge0LMvx6BOPSuGE4xpPki4s2ec-JTNQ5G5iwdE9zALmUYa52zH20XnF5jwBn-I1pEir07oTDjalFafiCgcVCRlF68i3zR360S5-uR85Lr13fYuVQNWeJRc8hnbHHdB7Fbnxi0ybaG7Kafy8MnjISWav3N2DgzPecGRliSys_F90rzlf1gnPfMJRhKZkA")
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(content().string("Deposito realizado com sucesso"));

        Conta contaAtualizada = contaRepository.findById(conta.getId()).get();
        Assertions.assertEquals(contaAtualizada.getSaldo(), "60.00");

        verify(autorizadorClient, times(1)).autorizar();
    }

    @Test
    void testRealizarDepositoNaoAutorizado() throws Exception {
        // Arrange
        var autorizadorResponse = new AutorizadorResponse("fail", new DataClient(false));
        when(autorizadorClient.autorizar()).thenReturn(autorizadorResponse );
        NovoDepositoRequest request = new NovoDepositoRequest(conta.getId(), BigDecimal.valueOf(50));

        // Act & Assert
        mockMvc.perform(post("/api/contas/depositar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLXNlcnZlciIsInN1YiI6Imtpa28gZm9ybWlnYSIsImlhdCI6MTc2MTE1ODc3MywiZXhwIjoxNzYxMTc2NzczLCJqdGkiOiIyOTY1ZTU2Ny0zNDE2LTQ1ZDQtODgwYy0zM2U3NGZlZjg1N2MiLCJ1c2VySWQiOiIxMDIzNTk0Yi1mZmI1LTQ5ZjMtYWRhYy1kYmEwYmRmMjhiZjgifQ.kiotGrYqb8sY3zVO7X80UymGpqKZR0r71227yuThD9h26cwvTN8lmLjCZ8c5ZpJvj-MugyH-3V8UhzSmXiysPOKmtC-Q-5v0tDEWKYQ00pMlqGRhG8GlFU6tpTIwcSW_VK3h26HAZJnBiYFKfE_FLt9KG6ge0LMvx6BOPSuGE4xpPki4s2ec-JTNQ5G5iwdE9zALmUYa52zH20XnF5jwBn-I1pEir07oTDjalFafiCgcVCRlF68i3zR360S5-uR85Lr13fYuVQNWeJRc8hnbHHdB7Fbnxi0ybaG7Kafy8MnjISWav3N2DgzPecGRliSys_F90rzlf1gnPfMJRhKZkA")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Depósito não autorizado pelo sistema externo."));

    }


    @Test
    void testRealizarDepositoErroAutorizadorFeign() throws Exception {
        // Arrange
        when(autorizadorClient.autorizar()).thenThrow(FeignException.class);
        NovoDepositoRequest request = new NovoDepositoRequest(conta.getId(), BigDecimal.valueOf(50));

        // Act & Assert
        mockMvc.perform(post("/api/contas/depositar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLXNlcnZlciIsInN1YiI6Imtpa28gZm9ybWlnYSIsImlhdCI6MTc2MTE1ODc3MywiZXhwIjoxNzYxMTc2NzczLCJqdGkiOiIyOTY1ZTU2Ny0zNDE2LTQ1ZDQtODgwYy0zM2U3NGZlZjg1N2MiLCJ1c2VySWQiOiIxMDIzNTk0Yi1mZmI1LTQ5ZjMtYWRhYy1kYmEwYmRmMjhiZjgifQ.kiotGrYqb8sY3zVO7X80UymGpqKZR0r71227yuThD9h26cwvTN8lmLjCZ8c5ZpJvj-MugyH-3V8UhzSmXiysPOKmtC-Q-5v0tDEWKYQ00pMlqGRhG8GlFU6tpTIwcSW_VK3h26HAZJnBiYFKfE_FLt9KG6ge0LMvx6BOPSuGE4xpPki4s2ec-JTNQ5G5iwdE9zALmUYa52zH20XnF5jwBn-I1pEir07oTDjalFafiCgcVCRlF68i3zR360S5-uR85Lr13fYuVQNWeJRc8hnbHHdB7Fbnxi0ybaG7Kafy8MnjISWav3N2DgzPecGRliSys_F90rzlf1gnPfMJRhKZkA")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadGateway());
    }
}
