package com.jw.relatorios_territorios.Controller;


import com.jw.relatorios_territorios.DTO.NotificacaoDTO;
import com.jw.relatorios_territorios.Models.Notificacao;
import com.jw.relatorios_territorios.Services.NotificacaoServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoServices notificacaoServices;

    @GetMapping
    public ResponseEntity<List<NotificacaoDTO>> buscarNotificacoesComuns(){
        try{
            List<NotificacaoDTO> notificacoes = this.notificacaoServices.buscarNotificacoesComuns();
            return ResponseEntity.ok().body(notificacoes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ArrayList<>());
        }
    }

    @PutMapping("/lidas")
    public ResponseEntity<List<NotificacaoDTO>> marcarComoLidas(@RequestBody List<NotificacaoDTO> notificacaoDTO){
        try{
            return ResponseEntity.ok().body(this.notificacaoServices.marcarComoLidas(notificacaoDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
