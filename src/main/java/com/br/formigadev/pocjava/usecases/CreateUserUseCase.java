package com.br.formigadev.pocjava.usecases;

import com.br.formigadev.pocjava.controller.dto.NovoUsuarioResponse;
import com.br.formigadev.pocjava.controller.dto.NovoUsuarioRequest;
import com.br.formigadev.pocjava.entities.User;
import com.br.formigadev.pocjava.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public NovoUsuarioResponse execute(NovoUsuarioRequest userInput){
        User user = userInput.toEntity();
        boolean emailInUse = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean cpfInUse =  userRepository.findByCpf(user.getCpf()).isPresent();
        if (emailInUse){
            throw new RuntimeException("Usuario com o email informado já existe");
        }
        if (cpfInUse){
            throw new RuntimeException("Cpf duplicado");
        }
        User savedUser = userRepository.save(user);
        return new NovoUsuarioResponse(savedUser.getUserId().toString(), savedUser.getEmail(), savedUser.getName());
    }
}
