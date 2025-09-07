package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.RelatorioDTO;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Models.Relatorio;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import com.jw.relatorios_territorios.Repository.RelatorioRepository;
import com.jw.relatorios_territorios.Repository.RevisitaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RelatorioServices {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private PublicadorRepository publicadorRepository;

    @Autowired
    private RevisitaRepository revisitaRepository;

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private EmailService emailService;

    public void salvar(RelatorioDTO relatorioDTO){

        //pegar usuario em questão
        Publicador publicador = getPublicador();

        //pegar numero de revisitas
        Integer countRevisitas = getCountRevisitas(publicador.getId());

        //montar objeto para ser salvo
        Relatorio relatorio = new Relatorio();
        relatorio.setNome(publicador.getNome());
        relatorio.setHoras(relatorioDTO.horas());
        relatorio.setIdPublicador(publicador.getId());
        relatorio.setRevisitas(countRevisitas);
        relatorio.setGrupoCampo(publicador.getGrupoCampo().getId());

        try{
            this.relatorioRepository.save(relatorio);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void verificarRelatorioParaEnvio(){
        List<Object[]> listRelatorios = this.relatorioRepository.findByRelatoriosForSend();
        if(!listRelatorios.isEmpty()){
            listRelatorios.stream()
                .map( a -> {
                    this.emailService.enviarEmailRelatorioPublicador(a);
                    return true;
                })
                .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    private Publicador getPublicador(){
        String email = this.tokenServices.tokenToEmail();
        try{
            return this.publicadorRepository.findByEmail(email).get();
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    private Integer getCountRevisitas(UUID idPublicador){
        try{
            return this.revisitaRepository.findByCountRevisita(idPublicador);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
