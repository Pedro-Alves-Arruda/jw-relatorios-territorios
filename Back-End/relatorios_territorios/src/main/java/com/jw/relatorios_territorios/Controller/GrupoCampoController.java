package com.jw.relatorios_territorios.Controller;

import com.jw.relatorios_territorios.DTO.GrupoCampoDTO;
import com.jw.relatorios_territorios.Services.GrupoCampoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("grupo-campo")
public class GrupoCampoController {

    @Autowired
    private GrupoCampoServices grupoCampoServices;

    @GetMapping
    public ResponseEntity<List<GrupoCampoDTO>> listar(@RequestParam String email){
        try{
            return ResponseEntity.ok().body(grupoCampoServices.listar(email));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
