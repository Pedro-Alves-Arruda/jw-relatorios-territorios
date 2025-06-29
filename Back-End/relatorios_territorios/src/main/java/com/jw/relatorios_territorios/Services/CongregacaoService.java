package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.Models.Congregacao;
import com.jw.relatorios_territorios.Repository.CongregacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongregacaoService {


    @Autowired
    private CongregacaoRepository congregacaoRepository;

    public List<Congregacao> listar(){
        return congregacaoRepository.findAll();
    }
}
