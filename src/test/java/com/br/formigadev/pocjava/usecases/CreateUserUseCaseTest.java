package com.br.formigadev.pocjava.usecases;

import com.br.formigadev.pocjava.controller.dto.NovoUsuarioRequest;
import com.br.formigadev.pocjava.controller.dto.NovoUsuarioResponse;
import com.br.formigadev.pocjava.entities.User;
import com.br.formigadev.pocjava.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CreateUserUseCase createUserUseCase;
    @Test
    void deveCriarUsuarioComSucesso() {
        // Arrange
        NovoUsuarioRequest request = new NovoUsuarioRequest("nome", "109.446.720-00", "12345678900", "johndoe@gmail.com");
        User userEntity = request.toEntity();
        userEntity.setUserId(UUID.randomUUID());

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByCpf(userEntity.getCpf())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        // Act
        NovoUsuarioResponse response = createUserUseCase.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals(userEntity.getUserId().toString(), response.userId());
        assertEquals(userEntity.getEmail(), response.email());
        assertEquals(userEntity.getName(), response.name());

    }
    @Test
    void deveLancarEXEmailDuplicado() {
        // Arrange
        NovoUsuarioRequest request = new NovoUsuarioRequest("nome", "109.446.720-00", "12345678900", "johndoe@gmail.com");
        User userEntity = request.toEntity();
        userEntity.setUserId(UUID.randomUUID());

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(userEntity));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () ->createUserUseCase.execute(request));

        assertEquals("Usuario com o email informado já existe", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));


    }
    @Test
    void deveLancarEXCpfDuplicado() {
        // Arrange
        NovoUsuarioRequest request = new NovoUsuarioRequest("nome", "109.446.720-00", "12345678900", "johndoe@gmail.com");
        User userEntity = request.toEntity();
        userEntity.setUserId(UUID.randomUUID());

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByCpf(userEntity.getCpf())).thenReturn(Optional.of(userEntity));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () ->createUserUseCase.execute(request));

        assertEquals("Cpf duplicado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));


    }


}