package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.Models.TipoDesignacao;
import com.jw.relatorios_territorios.Repository.TipoDesignacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDesignacaoServices {

    @Autowired
    private TipoDesignacaoRepository tipoDesignacaoRepository;


    public List<TipoDesignacao> listar(){
        try{
            return tipoDesignacaoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
