package com.jw.relatorios_territorios.Controller;


import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Models.Token;
import com.jw.relatorios_territorios.Services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginServices loginServices;


    @PostMapping
    public Token login(@RequestBody PublicadorDTO publicadorDTO){

        Token token = new Token();
        token.setToken(loginServices.verificarLogin(publicadorDTO));
        return token;

    }


}
