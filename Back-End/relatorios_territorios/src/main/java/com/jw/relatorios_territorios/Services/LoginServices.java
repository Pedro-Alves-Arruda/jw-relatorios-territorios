package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class LoginServices {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenServices tokenServices;


    @Autowired
    private PublicadorRepository publicadorRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    private static final Logger log = LoggerFactory.getLogger(LoginServices.class);

    @Autowired
    private KafkaServices kafkaServices;



    public String verificarLogin(PublicadorDTO publicadorDTO){
        Optional<Publicador> publicadorDesejado = publicadorRepository.findByEmail(publicadorDTO.email());

        if(publicadorDesejado.get() instanceof  Publicador &&
            passwordEncoder.matches(publicadorDTO.password(), publicadorDesejado.get().getPassword())){
            return tokenServices.generateToken(publicadorDesejado.get());
        }

        return "";
    }

    public void enviarLinkRedefinicaoSenha(String email) throws ExecutionException, InterruptedException {
        try{
            Publicador publicador = publicadorRepository.findByEmail(email).get();
            String token = tokenServices.gerarTokenPersonalizado(publicador, 5);
            if(!token.isEmpty()) {
                this.emailService.send(email, token);
            }else{
                this.kafkaServices.deletarUltimaMensagem("redefinicao-senha");
                throw new EntityNotFoundException("Email não existe no sistema");
            }
        } catch (Exception e) {
            this.kafkaServices.deletarUltimaMensagem("redefinicao-senha");
            throw new RuntimeException(e);
        }
    }

    public void atualizarSenha(PublicadorDTO publicadorDTO) throws ExecutionException, InterruptedException {
        try {
            //pegar usuario em questão
            Publicador publicador = this.publicadorRepository.findByEmail(publicadorDTO.email()).get();
            //setando a senha nova
            publicador.setPassword(encoder.encode(publicadorDTO.password()));
            //salvando
            this.publicadorRepository.save(publicador);
            log.info("Senha alterada com sucesso");

        }catch (EntityNotFoundException e){
            this.kafkaServices.deletarUltimaMensagem("atualizar-senha");
            throw new EntityNotFoundException(e.getMessage());
        }catch (Exception e) {
            this.kafkaServices.deletarUltimaMensagem("atualizar-senha");
            throw new RuntimeException(e);
        }
    }
}
