import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class ListarService {

  constructor(private client:HttpClient) { }

  listar():Observable<any>{
    return this.client.get(Enviroments.url+"/revisita/listar");
  }
}
