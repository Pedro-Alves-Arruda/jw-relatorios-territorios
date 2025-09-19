import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, UnsubscriptionError } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class LoginServicesService {

  constructor(private http:HttpClient) { }

  login(usurio:any):Observable<any>{
    return this.http.post(Enviroments.url+"/login", usurio)
  }

  solicitarLinkRedefinicaoSenha(email:any):Observable<any>{
    return this.http.get(Enviroments.url+"/login/solicitar-link-redefinicao-senha/"+email)
  }

  salvarSenhaNova(senhaNova:any):Observable<any>{
    return this.http.post(Enviroments.url+"/login/redefinir-senha",senhaNova, { responseType: 'text' });
  }

  enviarSolicitacaoUsuarioNovo(usuario:any):Observable<any>{
    return this.http.post(Enviroments.url+"/login/solicitar-criacao-usuario", usuario)
  }

}
