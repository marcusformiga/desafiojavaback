package com.br.formigadev.pocjava.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cobrancas")
public class Cobranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cobrancaId;
    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal valor;
    private String descricao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "originador_id", nullable = false)
    private User origem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private User destinatario;
    @Enumerated(EnumType.STRING)
    private CobrancaStatus status = CobrancaStatus.PENDENTE;
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
    private String cpfDestinatario;
    private String cpfOrigem;

    @Deprecated
    public Cobranca(){}

    public Cobranca(String cpfDestinatario, BigDecimal valorCobranca, String descricao, String cpfOrigem) {
        this.cpfDestinatario = cpfDestinatario;
        this.valor = valorCobranca;
        this.descricao= descricao;
        this.cpfOrigem = cpfOrigem;
    }


    public Long getCobrancaId() {
        return cobrancaId;
    }


    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public User getOrigem() {
        return origem;
    }

    public void setOrigem(User origem) {
        this.origem = origem;
    }

    public User getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(User destinatario) {
        this.destinatario = destinatario;
    }

    public CobrancaStatus getStatus() {
        return status;
    }

    public void setStatus(CobrancaStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getCpfDestinatario() {
        return cpfDestinatario;
    }

    public String getCpfOrigem() {
        return cpfOrigem;
    }
}
