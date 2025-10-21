package com.br.formigadev.pocjava.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "autorizadorClient",
        url = "https://zsy6tx7aql.execute-api.sa-east-1.amazonaws.com"
)
public interface AutorizadorClient {

    @GetMapping("/authorizer")
    AutorizadorResponse autorizar();
}

