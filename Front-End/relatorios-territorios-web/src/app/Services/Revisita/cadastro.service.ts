import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  constructor(private client:HttpClient) { }

  salvar(revisita:any): Observable<any>{
    return this.client.post(Enviroments.url+"/revisita/salvar", revisita)
  }
}
