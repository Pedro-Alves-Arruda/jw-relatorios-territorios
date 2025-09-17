package com.jw.relatorios_territorios.Controller;


import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Token;
import com.jw.relatorios_territorios.Producers.LoginProducers;
import com.jw.relatorios_territorios.Services.LoginServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Optional;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginServices loginServices;

    @Autowired
    private LoginProducers loginProducers;


    @PostMapping
    public ResponseEntity<Token> login(@RequestBody PublicadorDTO publicadorDTO){
        Token token = new Token();
        token.setToken(loginServices.verificarLogin(publicadorDTO));
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/solicitar-link-redefinicao-senha/{email}")
    public ResponseEntity<String> enviarLinkRedefinicaoSenha(@PathVariable String email){
        try{
            return ResponseEntity.ok().body(this.loginProducers.enviarMensagemTopicoRedefinicaoSenha(email));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody PublicadorDTO publicadorDTO){
        try{
            return ResponseEntity.ok().body(loginProducers.enviarMensagemAtualizarSenha(publicadorDTO));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("status-redefinir-senha")
    public ResponseEntity<String> mudarStatusRedefinicaoSenha(@RequestParam String email){
        System.out.println(email);
        return ResponseEntity.ok().body("Ok");
    }


}
