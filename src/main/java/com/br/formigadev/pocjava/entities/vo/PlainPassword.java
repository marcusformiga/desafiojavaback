package com.br.formigadev.pocjava.entities.vo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PlainPassword {

    private String value;

    public PlainPassword(String password){
        this.value= password;
    }

    public String hashPassword(){
        return new BCryptPasswordEncoder().encode(value);
    }
}
