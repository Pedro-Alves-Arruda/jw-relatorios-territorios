import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class ServicoCampoService {

  constructor(private client:HttpClient) { }

  salvarServicoCampo(servicoCampo:any):Observable<any>{
    return this.client.post(Enviroments.url+"/campo/servico-campo", servicoCampo, { responseType: 'text' });
  }
}
