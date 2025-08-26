package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.RevisitaDTO;
import com.jw.relatorios_territorios.Models.Revisita;
import com.jw.relatorios_territorios.Repository.RevisitaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

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
        revisita.setNome(revisitaDTO.nome());

        repository.save(revisita);
    }

    public List<Revisita> listar(){
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizar(RevisitaDTO revisitaDTO){
        //Buscando objeto que deve ser atualizado
        Optional<Revisita> usuarioDesejado =  repository.findById(revisitaDTO.id());

        if(!usuarioDesejado.isPresent()){
            throw new EntityNotFoundException("Usuario com id"+revisitaDTO.id()+" não encontrado");
        }

        //atualizando os campos não nulos
        setIfNotNull(revisitaDTO.estado(), usuarioDesejado.get()::setEstado);
        setIfNotNull(revisitaDTO.numero(), usuarioDesejado.get()::setNumero);
        setIfNotNull(revisitaDTO.rua(), usuarioDesejado.get()::setRua);
        setIfNotNull(revisitaDTO.nome(), usuarioDesejado.get()::setNome);
        setIfNotNull(revisitaDTO.telefone(), usuarioDesejado.get()::setTelefone);
        setIfNotNull(revisitaDTO.cidade(), usuarioDesejado.get()::setCidade);
        setIfNotNull(revisitaDTO.bairro(), usuarioDesejado.get()::setBairro);
        setIfNotNull(revisitaDTO.descricao(), usuarioDesejado.get()::setDescricao);
        setIfNotNull(revisitaDTO.cep(), usuarioDesejado.get()::setCep);
        usuarioDesejado.get().setCreated_at(LocalDateTime.now());

        //atualizando usuario
        repository.save(usuarioDesejado.get());

    }

    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

}
