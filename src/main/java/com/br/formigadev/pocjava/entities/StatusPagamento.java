package com.br.formigadev.pocjava.entities;

public enum StatusPagamento {
    APROVADO("Aprovado"),
    NEGADO("Negado"),
    FALHA("Falha"),
    EXTORNADO("Extornado"),
    AGUARDANDO("Aguardando");

    private String tipo;

    StatusPagamento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
