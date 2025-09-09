import { Component, Input, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from '../../../AuthService';
import $ from 'jquery';
import { WebSocketService } from '../../../Services/WebSocket/web-socket.service';
import { CommonModule } from '@angular/common';
import { NotificacoesService } from '../../../Services/Notificacoes/notificacoes.service';
import { interval, Subscription } from 'rxjs';



@Component({
  selector: 'app-menu',
  imports: [RouterOutlet, MatIconModule, CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  constructor(private router:Router, 
    private authService:AuthService, 
    private webSocketServices:WebSocketService,
    private notificacaoServices:NotificacoesService){}

  notificacoes:any[] = []
  private subscription!: Subscription;
  countNotificacoes = 0
  
  ngOnInit(){
    this.buscaNotificacaoInterval()
  
      this.webSocketServices.notificacaoSubject
        .subscribe(notificacao => {
          console.log(notificacao)
          if(!this.notificacoes.some(m => m.message == notificacao.message))
            this.notificacoes.push(notificacao)
            this.countNotificacoes = this.notificacoes.length
        })
  }

  buscaNotificacaoInterval(){
    this.subscription = interval(300000).subscribe(() => {
        this.buscarNotificacoes();
    });
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

  formataDataHora(dataHora:any){
    const date = new Date(dataHora);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); 
    const year = date.getFullYear();

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    if(day == new Date().getDate().toString())
      return `${hours}:${minutes}`;
    else
      return `${day}/${month}/${year} ${hours}:${minutes}`;
  }

  buscarNotificacoes(){
    this.notificacaoServices.buscarNotificacoes()
      .subscribe(res => {
        if(res)
          this.notificacoes.push(res)
          this.notificacoes = this.notificacoes[0]
          this.countNotificacoes = this.notificacoes.length
      })
  }
}
