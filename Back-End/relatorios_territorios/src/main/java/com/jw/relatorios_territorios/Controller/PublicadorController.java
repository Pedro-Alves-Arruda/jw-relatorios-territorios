package com.jw.relatorios_territorios.Controller;



import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Producers.PublicadorProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publicador")
public class PublicadorController {

    @Autowired
    private PublicadorProducers producers;

    @PostMapping("/salvar")
    public HttpStatus salvar(@RequestBody PublicadorDTO publicadorDTO){
        producers.enviarMensagemSalvar(publicadorDTO);
        return HttpStatus.OK;
    }

}
