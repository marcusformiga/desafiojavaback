package com.br.formigadev.pocjava.controller.dto;

import com.br.formigadev.pocjava.entities.Cobranca;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record NewBillRequest(@CPF @NotBlank String cpfDestinatario, @NotNull @DecimalMin(value = "1.00", message = "O valor da cobranca deve ser maior que 1.00") BigDecimal valorCobranca, String descricao, @CPF @NotBlank String cpfOrigem)  {


    public Cobranca toEntity(){
        return new Cobranca(this.cpfDestinatario, this.valorCobranca, this.descricao, this.cpfOrigem);
    }
    public boolean isMenorQueZero() {
        return this.valorCobranca().compareTo(BigDecimal.ZERO) < 0;
    }
}
