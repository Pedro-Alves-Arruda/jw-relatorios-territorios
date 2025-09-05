package com.jw.relatorios_territorios.Controller;


import com.jw.relatorios_territorios.DTO.ServicoCampoDTO;
import com.jw.relatorios_territorios.Producers.CampoProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/campo")
public class CampoController {

    @Autowired
    private CampoProducers campoProducers;

    @PostMapping("/servico-campo")
    public ResponseEntity<String> salvarServicoCampo(@RequestBody ServicoCampoDTO servicoCampoDTO){
        try{
            return ResponseEntity.ok().body(this.campoProducers.enviarMensagemTopicoServicoCampo(servicoCampoDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
