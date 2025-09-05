package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.RevisitaDTO;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Models.Revisita;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import com.jw.relatorios_territorios.Repository.RevisitaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;


@Service
public class RevisitaServices {

    @Autowired
    private RevisitaRepository repository;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private PublicadorRepository publicadorRepository;

    @Autowired
    private HttpServletRequest request;


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
        revisita.setIdPublicador(revisitaDTO.idPublicador());

        repository.save(revisita);
    }

    @Cacheable("listaRevisitas")
    public List<Revisita> listar(){
        try{
            //buscando id do usuario pelo token
            UUID idPublicador = getIdPublicador();
            return repository.findAllById(idPublicador);
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

    private UUID getIdPublicador(){

        String email = this.tokenServices.tokenToEmail();

        if(email == null){
            throw new MissingCsrfTokenException("Token invalido ou inexistente");
        }
        Publicador publicadorDesejado = new Publicador();

        try {
            publicadorDesejado = publicadorRepository.findByEmail(email).get();
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Usuario inexistente");
        }
        return publicadorDesejado.getId();
    }
}
