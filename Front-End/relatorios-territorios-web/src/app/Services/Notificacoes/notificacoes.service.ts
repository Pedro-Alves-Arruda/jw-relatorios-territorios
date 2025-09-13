import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';
import { Notificacao } from '../../Models/Notificacao';

@Injectable({
  providedIn: 'root'
})
export class NotificacoesService {

  constructor(private client: HttpClient) { }

  buscarNotificacoes():Observable<Notificacao[]>{
    return this.client.get<Notificacao[]>(Enviroments.url+`/notificacoes`)
  }

  buscarNotificacoesPessoais(email:any):Observable<Notificacao[]>{
    return this.client.get<Notificacao[]>(Enviroments.url+`/notificacoes/pessoais?email=${email}`)
  }

  salvarComoLidas(notificacoes:any):Observable<Notificacao[]>{
    return this.client.put<Notificacao[]>(Enviroments.url+`/notificacoes/lidas`, notificacoes)
  }
}
