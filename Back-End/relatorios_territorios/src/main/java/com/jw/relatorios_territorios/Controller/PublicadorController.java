package com.jw.relatorios_territorios.Controller;



import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Producers.PublicadorProducers;
import com.jw.relatorios_territorios.Services.PublicadorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return HttpStatus.OK;
    }

    @GetMapping("/listar")
    public List<Publicador> listar(){
        return publicadorServices.listar();
    }

    @GetMapping("/{id}")
    public Publicador findById(@PathVariable Integer id){
        return publicadorServices.findById(id);
    }

}
