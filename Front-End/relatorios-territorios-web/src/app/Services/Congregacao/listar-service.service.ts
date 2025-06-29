import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class ListarServiceService {

  constructor(private http:HttpClient) { }

  listar():Observable<any>{
    return this.http.get(Enviroments.url+"/congregacao/listar")
  }


}
