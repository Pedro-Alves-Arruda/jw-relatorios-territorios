package com.jw.relatorios_territorios.Configuration;


import com.jw.relatorios_territorios.Services.PublicadorServices;
import com.jw.relatorios_territorios.Services.TokenServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private PublicadorServices publicadorServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);

        if(token == null){
            throw new InvalidBearerTokenException("Token não pode ser nulo");
        }
        String email = tokenServices.recoverToken(token);

        try{
            var publicador = publicadorServices.findByEmail(email);
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USERE"));
            var authentication = new UsernamePasswordAuthenticationToken(publicador, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            throw new RuntimeException("Erro ao montar securityContext da requisição");
        }

        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
