package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.controller.NewUserInput;
import com.br.formigadev.pocjava.entities.vo.PlainPassword;
import com.br.formigadev.pocjava.entities.User;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record NewUserRequest(String name, @CPF(message = "cpf inválido") String cpf, String password, @Email(message = "email inválido") String email) implements NewUserInput {


    public User toEntity(){
        PlainPassword plainPassword = new PlainPassword(password);
        return new User(this.name, plainPassword, this.cpf, this.email);
    }

}
