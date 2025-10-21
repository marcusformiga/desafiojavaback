package com.br.formigadev.pocjava.client;

import java.time.LocalDateTime;

public record CartaoData(String numero, LocalDateTime dataExpiracao, String cvv){}
