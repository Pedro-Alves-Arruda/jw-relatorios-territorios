package com.jw.relatorios_territorios.Controller;

import com.jw.relatorios_territorios.Models.TipoDesignacao;
import com.jw.relatorios_territorios.Services.TipoDesignacaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("tipo-designacao")
public class TipoDesignacaoController {

    @Autowired
    private TipoDesignacaoServices tipoDesignacaoServices;

    @GetMapping
    public ResponseEntity<List<TipoDesignacao>> listar(){
        try{
            return ResponseEntity.ok().body(tipoDesignacaoServices.listar());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
