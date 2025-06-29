import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import * as jwt from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService{
    constructor(private route:Router){}

    private readonly USER_KEY = 'user';

    login(usuario: any){
        const user = { nome: usuario.nome, token:usuario.token, email:usuario.email, roles:usuario.roles, id:usuario.id}
        localStorage.setItem(this.USER_KEY, JSON.stringify(user));
    }

    logout(){
        localStorage.removeItem(this.USER_KEY);
        this.route.navigate(['/login'])
    }

    getUsuarioLogado(){
        const user = localStorage.getItem(this.USER_KEY);
        return user ? JSON.parse(user) : null
    }

    isTokenExpired(token:string): boolean{
        try{
            const decode: any = jwt.jwtDecode(token)
            const now = Date.now().valueOf() / 1000;
            return decode.exp < now
        }catch (err){
            return true;
        }
    }

    isAutheticated(){
        const token = this.getUsuarioLogado().token;
        return token != null && this.isTokenExpired(token)
    }
}