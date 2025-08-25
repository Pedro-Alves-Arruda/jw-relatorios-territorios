package com.jw.relatorios_territorios.Controller;


import com.jw.relatorios_territorios.DTO.RevisitaDTO;
import com.jw.relatorios_territorios.Models.Revisita;
import com.jw.relatorios_territorios.Producers.RevisitaProducers;
import com.jw.relatorios_territorios.Services.RevisitaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/revisita")
public class RevisitaController {

    @Autowired
    private RevisitaProducers revisitaProducers;

    @Autowired
    private RevisitaServices services;

    @PostMapping("/salvar")
    public HttpStatus salvar(@RequestBody RevisitaDTO revisitaDTO){
        revisitaProducers.sendMessage(revisitaDTO);
        return HttpStatus.OK;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Revisita>> listar(){
        return ResponseEntity.ok(services.listar());
    }

}
