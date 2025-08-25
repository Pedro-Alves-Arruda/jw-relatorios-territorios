package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.RevisitaDTO;
import com.jw.relatorios_territorios.Models.Revisita;
import com.jw.relatorios_territorios.Repository.RevisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevisitaServices {

    @Autowired
    private RevisitaRepository repository;

    public void salvar(RevisitaDTO revisitaDTO){
        //Montar obj para salvar
        Revisita revisita = new Revisita();
        revisita.setBairro(revisitaDTO.bairro());
        revisita.setCep(revisitaDTO.cep());
        revisita.setCidade(revisitaDTO.cidade());
        revisita.setDescricao(revisitaDTO.descricao());
        revisita.setNumero(revisitaDTO.numero());
        revisita.setEstado(revisitaDTO.estado());
        revisita.setTelefone(revisitaDTO.telefone());
        revisita.setRua(revisitaDTO.rua());
        revisita.setCreated_at(LocalDateTime.now());

        repository.save(revisita);
    }

    public List<Revisita> listar(){
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
