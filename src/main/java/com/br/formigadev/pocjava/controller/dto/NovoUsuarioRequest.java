package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.entities.vo.PlainPassword;
import com.br.formigadev.pocjava.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record NovoUsuarioRequest(@Schema(description = "Cpf do usuário", example = "109.446.720-00", type = "string") String name, @CPF(message = "cpf inválido") String cpf,@Schema(description = "Senha do usuário", example = "senhasecreata", type = "string")  String password, @Schema(description = "Email do usuário", example = "johndoe@gmail.com", type = "string") @Email(message = "email inválido") String email)  {


    public User toEntity(){
        PlainPassword plainPassword = new PlainPassword(password);
        return new User(this.name, plainPassword, this.cpf, this.email);
    }

}
