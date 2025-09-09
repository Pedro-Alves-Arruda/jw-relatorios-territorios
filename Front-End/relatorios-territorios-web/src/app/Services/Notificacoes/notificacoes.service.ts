import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class NotificacoesService {

  constructor(private client: HttpClient) { }

  buscarNotificacoes():Observable<any>{
    return this.client.get(Enviroments.url+`/notificacoes`)
  }

  salvarComoLidas(notificacoes:any):Observable<any>{
    return this.client.put(Enviroments.url+`/notificacoes/lidas`, notificacoes)
  }
}
