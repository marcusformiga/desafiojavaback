package com.br.formigadev.pocjava.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_contas")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long versao;
    private String numero;
    private String agencia;
    private BigDecimal saldo;
    @OneToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private User dono;

    @Deprecated
    public Conta(){}

    public Conta(String agencia, String numero, BigDecimal saldo, User donoId) {
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = saldo;
        this.dono = donoId;
    }

    public void debitar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de débito inválido");
        }
        if (saldo.compareTo(valor) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }
        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de crédito inválido");
        }
        this.saldo = this.saldo.add(valor);
    }

    public Long getId() {
        return id;
    }

    public Long getVersao() {
        return versao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public User getDono() {
        return dono;
    }

    public void setDono(User dono) {
        this.dono = dono;
    }
}
