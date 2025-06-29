import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Enviroments } from '../../Enviroments/Enviroments';

@Injectable({
  providedIn: 'root'
})
export class LoginServicesService {

  constructor(private http:HttpClient) { }

  login(usurio:any):Observable<any>{
    return this.http.post(Enviroments.url+"/login", usurio)
  }

}
