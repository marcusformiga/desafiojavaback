package com.br.formigadev.pocjava.repository;

import com.br.formigadev.pocjava.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);

}
