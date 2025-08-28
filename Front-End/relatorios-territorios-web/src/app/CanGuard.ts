
import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from './AuthService';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class CanGuard implements CanActivate {
	constructor(private authService: AuthService, private router: Router) {}

	canActivate(): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
		const usuario = this.authService.getUsuarioLogado();
		if (usuario && usuario.token && !this.authService.isTokenExpired(usuario.token)) {
			return true;
		} else {
			// Redireciona para /login se não autenticado ou token inválido
			return this.router.parseUrl('/login');
		}
	}
}
