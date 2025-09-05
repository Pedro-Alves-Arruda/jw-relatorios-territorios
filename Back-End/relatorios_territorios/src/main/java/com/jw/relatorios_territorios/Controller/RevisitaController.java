package com.jw.relatorios_territorios.Controller;



import com.jw.relatorios_territorios.DTO.RevisitaDTO;
import com.jw.relatorios_territorios.Models.Revisita;
import com.jw.relatorios_territorios.Producers.RevisitaProducers;
import com.jw.relatorios_territorios.Services.RevisitaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/revisita")
public class RevisitaController {

    @Autowired
    private RevisitaProducers revisitaProducers;

    @Autowired
    private RevisitaServices services;

    @PostMapping("/salvar")
    public ResponseEntity<String> salvar(@RequestBody RevisitaDTO revisitaDTO){
        revisitaProducers.enviarMessagem(revisitaDTO);
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("/listar/{email}")
    public ResponseEntity<List<Revisita>> listar(@PathVariable String email){
        return ResponseEntity.ok(services.listar(email));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody RevisitaDTO revisitaDTO){
        try{
            revisitaProducers.enviarMessagemAtualizar(revisitaDTO);
            return ResponseEntity.ok("Objeto atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
