package com.jw.relatorios_territorios.Configuration;


import com.jw.relatorios_territorios.Services.PublicadorServices;
import com.jw.relatorios_territorios.Services.TokenServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Collections;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private static final Logger log = LoggerFactory.getLogger(JWTInterceptorConfig.class);

    private final JWTInterceptorConfig jwtInterceptorConfig;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private PublicadorServices publicadorServices;

    public WebSocketConfig(JWTInterceptorConfig jwtInterceptorConfig){
        this.jwtInterceptorConfig = jwtInterceptorConfig;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("ws")
                .setAllowedOrigins("http://localhost:4200");
    }
}
