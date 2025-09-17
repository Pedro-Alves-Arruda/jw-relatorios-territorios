package com.jw.relatorios_territorios.Services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.jw.relatorios_territorios.Models.Publicador;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServices {

    @Value("${TOKEN_SECRET}")
    private String secret;

    @Autowired
    private HttpServletRequest request;

    public String generateToken(Publicador publicador){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        try{
            return JWT.create()
                    .withIssuer("relatorios-territorios")
                    .withClaim("isAnciao", publicador.isAnciao())
                    .withSubject(publicador.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusHours((2)).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
        }catch (JWTCreationException ex){
            throw new RuntimeException(ex.getMessage());
        }

    }

    public String gerarTokenPersonalizado(Publicador publicador, Integer duracao){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        try{
            return JWT.create()
                    .withIssuer("relatorios-territorios")
                    .withSubject(publicador.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusMinutes(duracao).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
        }catch (JWTCreationException ex){
            throw new RuntimeException(ex.getMessage());
        }

    }

    public String recoverToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try{
            return JWT.require(algorithm)
                    .withIssuer("relatorios-territorios")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTCreationException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public String tokenToEmail(){
        String token = this.request.getHeader("Autorization");
        token = token.replace("Bearer", "");
        return this.recoverToken(token);

    }
}
