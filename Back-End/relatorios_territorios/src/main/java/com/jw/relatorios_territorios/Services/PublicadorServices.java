package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Congregacao;
import com.jw.relatorios_territorios.Models.GrupoCampo;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Repository.CongregacaoRepository;
import com.jw.relatorios_territorios.Repository.GrupoCampoRepository;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublicadorServices {

    @Autowired
    private GrupoCampoRepository grupoCampoRepository;

    @Autowired
    private CongregacaoRepository congregacaoRepository;

    @Autowired
    private PublicadorRepository publicadorRepository;

    public void salvar(PublicadorDTO publicadorDTO){
        //buscar congregacao e grupo campo
        Optional<GrupoCampo> grupoCampo = grupoCampoRepository.findById(publicadorDTO.grupoCampo());
        Optional<Congregacao> congregacao = congregacaoRepository.findById(publicadorDTO.congregacao());

        //Map para modelo
        Publicador publicador = new Publicador();
        publicador.setNome(publicadorDTO.nome());
        publicador.setCpf(publicadorDTO.cpf());
        publicador.setEmail(publicadorDTO.email());
        publicador.setGrupoCampo(grupoCampo.get());
        String[] roles = {publicadorDTO.funcao()};
        publicador.setRoles(roles);
        publicador.setCongregacao(congregacao.get());

        //salvar
        publicadorRepository.save(publicador);
    }
}
