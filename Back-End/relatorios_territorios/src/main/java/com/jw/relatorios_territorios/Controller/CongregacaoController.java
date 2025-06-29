package com.jw.relatorios_territorios.Controller;


import com.jw.relatorios_territorios.Models.Congregacao;
import com.jw.relatorios_territorios.Services.CongregacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("congregacao")
public class CongregacaoController {


    @Autowired
    private CongregacaoService congregacaoService;

    @GetMapping("listar")
    public List<Congregacao> listar(){
        return congregacaoService.listar();
    }


}
