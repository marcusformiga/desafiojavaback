package com.br.formigadev.pocjava.entities;

import com.br.formigadev.pocjava.entities.vo.PlainPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

class ContaTest {

    @Test
    @DisplayName("Deve creditar um valor com sucesso")
    void creditarValorComSucesso() {
        var dono = new User("John doe", new PlainPassword("teste1234"), "958.269.480-78", "johndoe@gmail.com");
        var conta = new Conta("agencia teste", "001", new BigDecimal(BigInteger.TEN), dono);
        conta.creditar(new BigDecimal("100"));
        Assertions.assertEquals(new BigDecimal("110"), conta.getSaldo());
    }

    @Test
    @DisplayName("Não deve creditar saldo quando o valor adicionado for zero")
    void creditarValorZero() {
        var dono = new User("John doe", new PlainPassword("teste1234"), "958.269.480-78", "johndoe@gmail.com");
        var conta = new Conta("agencia teste", "001", new BigDecimal(BigInteger.TEN), dono);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> conta.creditar(new BigDecimal("0")));
        Assertions.assertEquals("Valor de crédito inválido", ex.getMessage());

    }

    @Test
    @DisplayName("Não deve creditar saldo quando o valor adicionado for negativo")
    void creditarValorNegativo() {
        var dono = new User("John doe", new PlainPassword("teste1234"), "958.269.480-78", "johndoe@gmail.com");
        var conta = new Conta("agencia teste", "001", new BigDecimal(BigInteger.TEN), dono);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> conta.creditar(new BigDecimal("-1")));
        Assertions.assertEquals("Valor de crédito inválido", ex.getMessage());

    }
    @Test
    @DisplayName("Deve debitar um valor com sucesso")
    void debitarValorComSucesso() {
        var dono = new User("John doe", new PlainPassword("teste1234"), "958.269.480-78", "johndoe@gmail.com");
        var conta = new Conta("agencia teste", "001", new BigDecimal(BigInteger.TEN), dono);
        conta.debitar(new BigDecimal("5"));
        Assertions.assertEquals(new BigDecimal("5"), conta.getSaldo());
    }
    @Test
    @DisplayName("Não deve debitar saldo quando o valor retirado for negativo")
    void debitarValorNegativo() {
        var dono = new User("John doe", new PlainPassword("teste1234"), "958.269.480-78", "johndoe@gmail.com");
        var conta = new Conta("agencia teste", "001", new BigDecimal(BigInteger.TEN), dono);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> conta.debitar(new BigDecimal("-1")));
        Assertions.assertEquals("Valor de débito inválido", ex.getMessage());

    }
    @Test
    @DisplayName("Não deve debitar saldo quando o valor retirado for zero")
    void debitarValorZero() {
        var dono = new User("John doe", new PlainPassword("teste1234"), "958.269.480-78", "johndoe@gmail.com");
        var conta = new Conta("agencia teste", "001", new BigDecimal(BigInteger.TEN), dono);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> conta.debitar(new BigDecimal("0")));
        Assertions.assertEquals("Valor de débito inválido", ex.getMessage());

    }

}