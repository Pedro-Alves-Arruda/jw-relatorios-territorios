package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Congregacao;
import com.jw.relatorios_territorios.Models.GrupoCampo;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Repository.CongregacaoRepository;
import com.jw.relatorios_territorios.Repository.GrupoCampoRepository;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    public List<PublicadorDTO> listar(){
        try{
                    List<Publicador> publicadores = publicadorRepository.findAll()
                    .stream()
                    .map(publicador -> {
                        publicador.getCongregacao().setPublicadores(null);
                        return publicador;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));

                    return preparaListaRetorno(publicadores);
        }catch (JDBCException ex){
            throw new JDBCException(ex.getMessage(), ex.getSQLException());
        }
    }

    private List<PublicadorDTO> preparaListaRetorno(List<Publicador> publicadores){
        try{
            List<PublicadorDTO> publicadoresDto = new ArrayList<>();
            for(Publicador publicador : publicadores){
                publicadoresDto.add(new PublicadorDTO(publicador.getId(), publicador.getNome(), publicador.getCpf(), publicador.getEmail(), publicador.getTelefone(),
                        "", publicador.getGrupoCampo().getId(), publicador.getCongregacao().getId(), ""));
            }
            return publicadoresDto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao construir lista de publicadores. Erro: " + e.getMessage());
        }
    }

    public Publicador findByEmail(String email){
        try {
            var publicador = publicadorRepository.findByEmail(email);
            if(publicador.isPresent()){
                return publicador.get();
            }
            throw new EntityNotFoundException("Nenhum usuario encontrado");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Publicador findById(UUID id){
        try {
            Publicador publicador = publicadorRepository.findById(id).get();
            publicador.getCongregacao().setPublicadores(null);
            return publicador;
        }catch (JDBCException ex){
            throw new JDBCException(ex.getMessage(), ex.getSQLException());
        }
    }
}
