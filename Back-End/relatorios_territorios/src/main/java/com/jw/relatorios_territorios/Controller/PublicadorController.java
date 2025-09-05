package com.jw.relatorios_territorios.Controller;



import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Producers.PublicadorProducers;
import com.jw.relatorios_territorios.Services.PublicadorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/publicador")
public class PublicadorController {

    @Autowired
    private PublicadorProducers producers;

    @Autowired
    private PublicadorServices publicadorServices;

    @PostMapping("/salvar")
    public HttpStatus salvar(@RequestBody PublicadorDTO publicadorDTO){
        producers.enviarMensagemSalvar(publicadorDTO);
        return HttpStatus.CREATED;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PublicadorDTO>> listar(){
        return ResponseEntity.ok().body(publicadorServices.listar());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Publicador> findByEmail(@PathVariable String email){
        Publicador publicador = publicadorServices.findByEmail(email);
        publicador.setPassword(null);
        return ResponseEntity.ok().body(publicador);
    }


}
