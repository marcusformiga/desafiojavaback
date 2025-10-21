package com.br.formigadev.pocjava.entities;

public enum TipoPagamento {
    AVISTA("AVISTA"),
    CARTAO_CREDITO("CARTAO_DE_CREDITO");

    private String tipo;

    TipoPagamento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
