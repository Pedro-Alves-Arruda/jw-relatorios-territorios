import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  constructor(private http: HttpClient) { }

  salvar(publicador:any):Observable<any>{
    return this.http.post(Enviroments.url + "/publicador/salvar", publicador)
  }
}
