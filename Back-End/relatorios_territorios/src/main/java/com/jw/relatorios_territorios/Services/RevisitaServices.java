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
    private PublicadorRepository publicadorRepository;


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
        Publicador publicador = this.publicadorRepository.findByEmail(revisitaDTO.email()).get();
        revisita.setIdPublicador(publicador.getId());

        repository.save(revisita);
    }

    @Cacheable("listaRevisitas")
    public List<Revisita> listar(String email){
        try{
            //buscando id do usuario pelo token
            UUID idPublicador = getIdPublicador(email);
            return repository.findAllById(idPublicador);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizar(RevisitaDTO revisitaDTO){
        //Buscando objeto que deve ser atualizado
        Optional<Revisita> revisitaDesejada =  repository.findById(revisitaDTO.id());

        if(!revisitaDesejada.isPresent()){
            throw new EntityNotFoundException("Usuario com id"+revisitaDTO.id()+" não encontrado");
        }

        //atualizando os campos não nulos
        setIfNotNull(revisitaDTO.estado(), revisitaDesejada.get()::setEstado);
        setIfNotNull(revisitaDTO.numero(), revisitaDesejada.get()::setNumero);
        setIfNotNull(revisitaDTO.rua(), revisitaDesejada.get()::setRua);
        setIfNotNull(revisitaDTO.nome(), revisitaDesejada.get()::setNome);
        setIfNotNull(revisitaDTO.telefone(), revisitaDesejada.get()::setTelefone);
        setIfNotNull(revisitaDTO.cidade(), revisitaDesejada.get()::setCidade);
        setIfNotNull(revisitaDTO.bairro(), revisitaDesejada.get()::setBairro);
        setIfNotNull(revisitaDTO.descricao(), revisitaDesejada.get()::setDescricao);
        setIfNotNull(revisitaDTO.cep(), revisitaDesejada.get()::setCep);
        revisitaDesejada.get().setCreated_at(LocalDateTime.now());

        //atualizando usuario
        repository.save(revisitaDesejada.get());

    }

    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

    private UUID getIdPublicador(String email){
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
