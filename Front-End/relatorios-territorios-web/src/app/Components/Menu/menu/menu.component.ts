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
  notificacoesLidas:any[] = []
  
  ngOnInit(){
    this.buscarNotificacoes();
    this.buscaNotificacaoInterval()
  
      this.webSocketServices.notificacaoSubject
        .subscribe(notificacao => {
          if(!this.notificacoes.some(m => m.message == notificacao.message))
            this.notificacoes.push(notificacao)
            this.countNotificacoes = this.notificacoes.length
        })
      
      this.webSocketServices.notificacaoPessoalSubject
        .subscribe(notificacao => {
          this.notificacoes.push(notificacao)
        })


  }

  buscaNotificacaoInterval(){
    this.subscription = interval(300000).subscribe(() => {
      this.salvarComolidas(this.notificacoesLidas)  
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

  marcarComoLida(notificacao:any){
    $(`.${notificacao.id}`).css("background-color", "white")
    
    this.notificacoes.some(nt => {
        if(nt.id == notificacao.id)
          nt.lida = true
    })
    
    let notificacaoLida = {
      id: notificacao.id,
      lida: true
    }
    this.notificacoesLidas.push(notificacaoLida)
  }

  salvarComolidas(notificacoes:any){
    this.notificacaoServices.salvarComoLidas(notificacoes)
      .subscribe(res => {
        if(res)
          this.notificacoes.push(res)
          this.notificacoes = this.notificacoes[0]
          console.log(this.notificacoes.length)
          this.countNotificacoes = this.notificacoes.length
      })
  }
}
