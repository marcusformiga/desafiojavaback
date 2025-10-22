package com.br.formigadev.pocjava.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "tb_pagamentos")
@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    private LocalDate dataPagamento;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal valorPago;
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;
    private Integer numeroParcelas;
    @OneToOne
    @JoinColumn(name = "cobranca_id")
    private Cobranca cobranca;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User pagador;
    @Enumerated(EnumType.STRING)
    private StatusPagamento status = StatusPagamento.AGUARDANDO;

    @Deprecated
    public Pagamento(){}

    public Pagamento(Cobranca cobranca, TipoPagamento tipoPagamento, User pagador, BigDecimal valorPago, LocalDate diaPagamento) {
        validarPagamento(cobranca, pagador);
        this.cobranca = cobranca;
        this.tipo = tipoPagamento;
        this.pagador = pagador;
        this.valorPago = valorPago;
        this.dataPagamento = diaPagamento;
    }
    public void validarPagamento(Cobranca cobranca, User pagador) {
        if (this.valorPago.compareTo(cobranca.getValor()) != 0)
            throw new IllegalArgumentException("O valor pago deve ser igual ao valor da cobrança.");

        if (cobranca.getStatus().equals(CobrancaStatus.PAGA))
            throw new IllegalStateException("Cobrança já foi paga.");

        if (cobranca.getDestinatario().equals(pagador))
            throw new IllegalArgumentException("O pagador não pode ser o mesmo que o destinatário.");
    }


    public BigDecimal getValorPago() {
        return valorPago;
    }

    public Long getId() {
        return id;
    }



    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public User getPagador() {
        return pagador;
    }

    public void setPagador(User pagador) {
        this.pagador = pagador;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}
