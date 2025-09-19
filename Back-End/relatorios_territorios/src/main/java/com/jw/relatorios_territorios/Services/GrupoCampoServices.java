package com.jw.relatorios_territorios.Services;

import com.jw.relatorios_territorios.DTO.GrupoCampoDTO;
import com.jw.relatorios_territorios.Repository.GrupoCampoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrupoCampoServices {

    @Autowired
    private GrupoCampoRepository grupoCampoRepository;

    public List<GrupoCampoDTO> listar(String email){
        return new ArrayList<>();
    }

}
