import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Enviroments } from '../../Enviroments/Enviroments';
import { Subject } from 'rxjs';
import { AuthService } from '../../AuthService';
import { Router } from '@angular/router';
import * as jwt from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private client!:Client;
  public notificacaoSubject = new Subject<any>();
  public notificacaoPessoalSubject = new Subject<any>();


  constructor() { 
    let usuarioLogado = new AuthService(new Router);
    
    if(usuarioLogado.getUsuarioLogado().token != null || usuarioLogado.isAutheticated()){
      let token = usuarioLogado.getUsuarioLogado().token;
      let email = jwt.jwtDecode(token).sub
      this.client = new Client({
        brokerURL: `ws://localhost:8080/ws?token=${token}`,
        connectHeaders: {},
        reconnectDelay: 500,
        onConnect: () => {

          this.client.subscribe("/topic/notificacoes/relatorios", (msg) => {
            console.log(`recebendo mensagem no topico /topic/notificacoes/relatorios`)
            this.notificacaoSubject.next(msg.body)
          })

          this.client.subscribe(`/topic/notificacoes/${email}`, (msg) => {
            console.log(`recebendo mensagem no topico /topic/notificacoes/${email}`)
            this.notificacaoPessoalSubject.next(msg.body)
          })

        }
      });
    
      this.client.activate();
    }
  }

  conectar(rota:any){}
}
