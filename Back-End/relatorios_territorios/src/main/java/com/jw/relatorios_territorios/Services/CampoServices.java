package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.ServicoCampoDTO;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Models.ServicoCampo;
import com.jw.relatorios_territorios.Repository.CampoRepository;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampoServices{

    @Autowired
    private CampoRepository campoRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PublicadorRepository publicadorRepository;

    @Autowired
    private TokenServices tokenServices;

    public void salvarServicoCampo(ServicoCampoDTO servicoCampoDTO){
        //converter DTO para model
        ServicoCampo servicoCampo = servicoCampoDTOToSServicoCampo(servicoCampoDTO);
        try{
            //salvar campo
            this.campoRepository.save(servicoCampo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ServicoCampo servicoCampoDTOToSServicoCampo(ServicoCampoDTO servicoCampoDTO) {
        ServicoCampo servicoCampo = new ServicoCampo();
        servicoCampo.setDescricao(servicoCampoDTO.descricao());
        servicoCampo.setTempo(servicoCampoDTO.tempo());
        servicoCampo.setLisPublicacoesDeixadas(servicoCampoDTO.publicacoesDeixadas());
        Publicador publicador = getPublicador(servicoCampoDTO.email());
        servicoCampo.setPublicadorId(publicador);
        servicoCampo.setCreated_at(LocalDateTime.now());
        return servicoCampo;
    }

    private Publicador getPublicador(String email){
        try{
            return this.publicadorRepository.findByEmail(email).get();
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Usuario não existe");
        }
    }
}
