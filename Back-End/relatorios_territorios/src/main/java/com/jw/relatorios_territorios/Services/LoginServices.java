package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServices {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenServices tokenServices;


    @Autowired
    private PublicadorRepository publicadorRepository;



    public String verificarLogin(PublicadorDTO publicadorDTO){
        Optional<Publicador> publicadorDesejado = publicadorRepository.findByEmail(publicadorDTO.email());

        if(publicadorDesejado.get() instanceof  Publicador &&
            passwordEncoder.matches(publicadorDTO.password(), publicadorDesejado.get().getPassword())){
            return tokenServices.generateToken(publicadorDesejado.get());
        }

        return "";
    }
}
