import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class PublicacaoServicesService {

  constructor(private http:HttpClient) { }

  listar():Observable<any>{
    return this.http.get(Enviroments.url+"/publicacao")
  }
}
