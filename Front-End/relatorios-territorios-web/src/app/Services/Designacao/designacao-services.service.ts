import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Enviroments } from '../../Enviroments/Enviroments';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DesignacaoServicesService {

  constructor(private http:HttpClient) { }

  buscarDesignacoes(email:any):Observable<any>{
    return this.http.get(Enviroments.url+"/designacao?email="+email)
  }

  nova(designacao:any):Observable<any>{
    return this.http.post(Enviroments.url+"/designacao", designacao, { responseType: 'text' })
  }

}
