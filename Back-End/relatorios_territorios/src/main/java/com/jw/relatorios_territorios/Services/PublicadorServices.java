package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.*;
import com.jw.relatorios_territorios.Models.Congregacao;
import com.jw.relatorios_territorios.Models.EstudoBiblico;
import com.jw.relatorios_territorios.Models.GrupoCampo;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Repository.*;
import com.jw.relatorios_territorios.S3.PublicadorS3;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private PublicadorS3 publicadorS3;

    @Autowired
    private EstudoBiblicoRepository estudoBiblicoRepository;

    @Autowired
    private CampoRepository campoRepository;


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

    public void salvarFotoPerfil(FotoPerfilDTO fotoPerfilDTO) {
        try {
            Publicador publicador = this.findByEmail(fotoPerfilDTO.email());
            this.publicadorS3.salvarFotoPerfil(fotoPerfilDTO.imagem(), fotoPerfilDTO.email(), publicador.getId());
        } catch (Exception e) {

        }
    }

    public FotoPerfilDTO getFotoPerfil(String email){
        try{
            Publicador publicador = findByEmail(email);
            var imagem = this.publicadorS3.getFotoPerfil(email, publicador.getId());
            return new FotoPerfilDTO(imagem, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DadosGraficoDTO buscarDadosGrafico(String email){
        try{
            Publicador publicador = publicadorRepository.findByEmail(email).get();
            //buscando dados para grafico de pizza
            var obj = getDadosGraficoPizza(publicador);
            //buscando dadoss para grafico de linha
            var objGraficoLinha = getDadosGraficoLinha(publicador);
            return new DadosGraficoDTO(
                    new GraficoPizzaDTO(Integer.valueOf(obj[0].toString()), Integer.valueOf(obj[1].toString()), Integer.valueOf(obj[2].toString())),
                    objGraficoLinha
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] getDadosGraficoPizza(Publicador publicador) {
        List<Object[]> dadosGrafico = publicadorRepository.buscarDadosGrafico(publicador.getId());
        var obj = dadosGrafico.get(0);
        return obj;
    }

    private List<GraficoLinhaDTO> getDadosGraficoLinha(Publicador publicador){
        List<Object[]> dadosGrafico = publicadorRepository.buscarDadosGraficoLinha(publicador.getId());
        List<GraficoLinhaDTO> graficoLinha = new ArrayList<>();
        for(Object[] obj: dadosGrafico){
            graficoLinha.add(new GraficoLinhaDTO(obj[0].toString(), Integer.valueOf(obj[1].toString())));
        }
        return graficoLinha;
    }

    public List<EstudoBiblicoDTO> listarEstudosBiblicos(String email){
        try{
            Publicador publicador = publicadorRepository.findByEmail(email).get();
            List<EstudoBiblico> listEstudoBiblico = estudoBiblicoRepository.findByIdPublicador(publicador.getId());
            List<EstudoBiblicoDTO> listRetorno = new ArrayList<>();
            listEstudoBiblico.stream()
                    .map(a -> {
                        listRetorno.add(new EstudoBiblicoDTO(a.getNomeEstudante(), null, null, a.getPublicacao(), a.getCapitulo(), null));
                        return true;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
            return listRetorno;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<PublicacoesDeixadasDTO> listarPublicacoesDeixadas(String email){
        try{
            Publicador publicador = publicadorRepository.findByEmail(email).get();
            List<String> publicacoesDeixadas = this.campoRepository.buscarPublicacoesDeixadas(publicador.getId());

            if(publicacoesDeixadas.isEmpty()){
                return new ArrayList<>();
            }
            String[] publicacoes =  publicacoesDeixadas.toString().split("'");

            return Arrays.stream(publicacoes).toList().stream()
                    .filter(pub -> pub.startsWith("Video") || pub.startsWith("Brochura") || pub.startsWith("Sentinela"))
                    .collect(Collectors.toMap(
                            pub -> pub,
                            pub -> 1,
                            Integer::sum
                    ))
                    .entrySet().stream()
                    .map(e -> new PublicacoesDeixadasDTO(e.getKey(), e.getValue()))
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException(e);
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

    @Cacheable("usuario_por_email")
    public Publicador findByEmail(String email){
        try {
            var publicador = publicadorRepository.findByEmail(email);
            if(publicador.isPresent()){
                publicador.get().getCongregacao().setPublicadores(null);
                publicador.get().getGrupoCampo().setPublicadores(null);
                publicador.get().setServicoCampo(null);
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
