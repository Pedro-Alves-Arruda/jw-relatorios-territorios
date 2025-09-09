import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Enviroments } from '../../Enviroments/Enviroments';
import { Subject } from 'rxjs';
import { AuthService } from '../../AuthService';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private client!:Client;
  public notificacaoSubject = new Subject<any>();


  constructor() { 
    let usuarioLogado = new AuthService(new Router).getUsuarioLogado();
    const socket = `ws://localhost:8080/ws?token=${usuarioLogado.token}`;
    this.client = new Client({
      brokerURL: `ws://localhost:8080/ws?token=${usuarioLogado.token}`,
      connectHeaders: {},
      reconnectDelay: 100,
      onConnect: () => {
        this.client.subscribe("/topic/notificacoes/relatorios", (msg) => {
          this.notificacaoSubject.next(msg.body)
        })
      }
    });
  
    this.client.activate();
  }

  conectar(rota:any){}
}
