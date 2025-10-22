package com.br.formigadev.pocjava.controller.cobrancas;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CancelarCobrancaController {



    @PutMapping(value = "/cobrancas/{cobrancaId}/cancelar")
    public void cancelar(@RequestBody CancelarCobrancaRequest request, @PathVariable Long cobrancaId){}
}
