import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Enviroments } from '../../Enviroments/Enviroments';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {

  constructor(private client:HttpClient) { }

  getPerfil(email:string):Observable<any>{
    return this.client.get(Enviroments.url+"/publicador/"+email);
  }
}
