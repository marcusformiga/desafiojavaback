package com.br.formigadev.pocjava.entities;

public enum CobrancaStatus {
    PENDENTE("Pendente"),
    PAGA("Paga"),
    CANCELADA("Cancelada");

    private String status;

    CobrancaStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void atualizarStatus(){}
}
