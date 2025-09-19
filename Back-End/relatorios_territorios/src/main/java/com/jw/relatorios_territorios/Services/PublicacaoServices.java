package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.PublicacaoDTO;
import com.jw.relatorios_territorios.Repository.PublicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicacaoServices {

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    public List<PublicacaoDTO> listar(){
        try{
            return publicacaoRepository.findAll()
                    .stream()
                    .map(pb -> new PublicacaoDTO(pb.getNome()))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
