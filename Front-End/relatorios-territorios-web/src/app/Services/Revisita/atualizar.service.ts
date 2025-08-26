import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class AtualizarService {

  constructor(private client:HttpClient) { }

  atualizar(revisita:any):Observable<any>{
    return this.client.put(Enviroments.url+"/revisita/atualizar", revisita)
  }

}
