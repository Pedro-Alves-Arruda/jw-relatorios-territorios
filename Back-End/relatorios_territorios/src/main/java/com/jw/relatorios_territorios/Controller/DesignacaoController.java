package com.jw.relatorios_territorios.Controller;


import com.jw.relatorios_territorios.DTO.DesignacaoDTO;
import com.jw.relatorios_territorios.Producers.DesignacaoProducers;
import com.jw.relatorios_territorios.Services.DesignacaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/designacao")
public class DesignacaoController {

    @Autowired
    private DesignacaoServices designacaoServices;

    @Autowired
    private DesignacaoProducers designacaoProducers;

    @GetMapping
    public ResponseEntity<List<DesignacaoDTO>> listar(@RequestParam String email){
        try{
            return ResponseEntity.ok().body(designacaoServices.listar(email));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<String> nova(@RequestBody DesignacaoDTO designacaoDTO){
        try{
            this.designacaoProducers.enviarMensagemSalvarNovaDesignacao(designacaoDTO);
            return ResponseEntity.ok().body("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
