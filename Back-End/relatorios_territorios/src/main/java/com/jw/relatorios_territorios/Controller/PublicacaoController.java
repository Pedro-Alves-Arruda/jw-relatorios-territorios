package com.jw.relatorios_territorios.Controller;

import com.jw.relatorios_territorios.DTO.PublicacaoDTO;
import com.jw.relatorios_territorios.Models.Publicacao;
import com.jw.relatorios_territorios.Services.PublicacaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("publicacao")
public class PublicacaoController {

    @Autowired
    private PublicacaoServices publicacaoServices;

    @GetMapping
    public ResponseEntity<List<PublicacaoDTO>> listar(){
        try{
            return ResponseEntity.ok().body(publicacaoServices.listar());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
