import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  constructor(private http: HttpClient) {}

  listarDesignacoes():Observable<any>{
    return this.http.get(Enviroments.url+"/tipo-designacao")
  }

  listar():Observable<any>{
    return this.http.get(Enviroments.url+"/publicador/listar")
  }

  salvar(publicador:any):Observable<any>{
    return this.http.post(Enviroments.url + "/publicador/salvar", publicador)
  }

  buscarDadosGrafico(email:any):Observable<any>{
    return this.http.get(Enviroments.url+"/publicador/dados-grafico?email="+email)
  }

  listarEstudosBiblicos(email:any):Observable<any>{
    return this.http.get(Enviroments.url+"/publicador/listar-estudos?email="+email)
  }

  listarPublicacoesDeixadas(email:any):Observable<any>{
    return this.http.get(Enviroments.url+"/publicador/lista-publicacoes-deixadas?email="+email)
  }
}
