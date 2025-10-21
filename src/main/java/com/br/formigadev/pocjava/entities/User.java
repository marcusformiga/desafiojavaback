package com.br.formigadev.pocjava.entities;

import com.br.formigadev.pocjava.entities.vo.PlainPassword;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tb_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;
    private String name;
    @Column(unique = true)
    private String cpf;
    private String password;
    @Column(unique = true)
    private String email;

    @OneToOne(mappedBy = "dono", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Conta conta;

    @OneToMany(mappedBy = "origem", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Cobranca> cobrancaCriadas = new HashSet<>();

    @OneToMany(mappedBy = "destinatario", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Cobranca> cobrancaRecebidas = new HashSet<>();

    @Deprecated
    public User(){}

    public User(String name, PlainPassword password, String cpf, String email) {
        this.name = name;
        this.password = password.hashPassword();
        this.cpf = cpf;
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Cobranca> getCobrancasCriadas() {
        return cobrancaCriadas;
    }

    public Set<Cobranca> getCobrancasRecebidas() {
        return cobrancaRecebidas;
    }

    public Conta getConta() {
        return conta;
    }
}
