package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Congregacao;
import com.jw.relatorios_territorios.Models.GrupoCampo;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Repository.CongregacaoRepository;
import com.jw.relatorios_territorios.Repository.GrupoCampoRepository;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicadorServices {

    @Autowired
    private GrupoCampoRepository grupoCampoRepository;

    @Autowired
    private CongregacaoRepository congregacaoRepository;

    @Autowired
    private PublicadorRepository publicadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        publicador.setTelefone(publicadorDTO.telefone());
        String[] roles = {publicadorDTO.funcao()};
        publicador.setRoles(roles);
        publicador.setCongregacao(congregacao.get());
        publicador.setPassword(passwordEncoder.encode(publicadorDTO.password()));

        //salvar
        publicadorRepository.save(publicador);
    }

    public List<Publicador> listar(){
        try{
                    return
                    publicadorRepository.findAll()
                    .stream()
                    .map(publicador -> {
                        publicador.getCongregacao().setPublicadores(null);
                        return publicador;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
        }catch (JDBCException ex){
            throw new JDBCException(ex.getMessage(), ex.getSQLException());
        }
    }

    public Publicador findById(Integer id){
        try {
            Publicador publicador = publicadorRepository.findById(id).get();
            publicador.getCongregacao().setPublicadores(null);
            return publicador;
        }catch (JDBCException ex){
            throw new JDBCException(ex.getMessage(), ex.getSQLException());
        }
    }
}
