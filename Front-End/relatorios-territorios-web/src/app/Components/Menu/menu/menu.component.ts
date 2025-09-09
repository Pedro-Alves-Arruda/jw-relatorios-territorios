import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from '../../../AuthService';
import $ from 'jquery';
import { WebSocketService } from '../../../Services/WebSocket/web-socket.service';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-menu',
  imports: [RouterOutlet, MatIconModule, CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  constructor(private router:Router, private authService:AuthService, private webSocketServices:WebSocketService){}

  notificacoes:any[] = []
  countNotificacoes = 0
  
  ngOnInit(){
    this.webSocketServices.notificacaoSubject
      .subscribe(msg => {
        if(!this.notificacoes.some(m => m == msg))
          this.notificacoes.push(msg)
          this.countNotificacoes = this.notificacoes.length
      })
  }

  redireciona(){
    if(this.authService.getUsuarioLogado().token != null)
      this.router.navigate(["/perfil"])
    else
      this.router.navigate(["/login"])
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/login'])
  }
}
