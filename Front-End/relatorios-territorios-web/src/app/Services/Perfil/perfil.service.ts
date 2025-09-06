import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Enviroments } from '../../Enviroments/Enviroments';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {

  constructor(private client:HttpClient) { }

  getPerfil(email:any):Observable<any>{
    return this.client.get(Enviroments.url+"/publicador/"+email);
  }

  atualizarPerfil(publicador:any):Observable<any>{
    return this.client.put(Enviroments.url+"/publicador/"+publicador.email, publicador)
  }
}
