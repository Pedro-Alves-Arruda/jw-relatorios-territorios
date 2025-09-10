import { Component, Input, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from '../../../AuthService';
import $ from 'jquery';
import { WebSocketService } from '../../../Services/WebSocket/web-socket.service';
import { CommonModule } from '@angular/common';
import { NotificacoesService } from '../../../Services/Notificacoes/notificacoes.service';
import { interval, Subscription } from 'rxjs';
import * as jwt from 'jwt-decode'



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
  notificacoesPessoais:any[] = []
  private subscription!: Subscription;
  countTotalNotificacoes:any = 0
  countNotificacoes:any = 0
  countNotificacoesPessoais:any = 0
  notificacoesLidas: any[] = []
  usuarioLogado:any
  
  ngOnInit(){
    this.usuarioLogado = this.authService.getUsuarioLogado();
    this.buscarNotificacoes();
    this.buscarNotificacoesPessoais();
    this.buscaNotificacaoInterval()
    this.marcarComoLidaInterval()

    this.countNotificacoes = this.notificacoes[0].filter((a: { lida: boolean; }) => a.lida==false).length;
    this.countNotificacoesPessoais = this.notificacoesPessoais[0].filter((a: { lida: boolean; }) => a.lida==false).length 
    this.countTotalNotificacoes = this.countNotificacoes + this.countNotificacoesPessoais;

      this.webSocketServices.notificacaoSubject
        .subscribe(notificacao => {
          console.log("recebendo notificacao geral")
          this.notificacoes[0].push(notificacao)
          this.countNotificacoes = this.countNotificacoes + 1
        })
        
        this.webSocketServices.notificacaoPessoalSubject
        .subscribe(notificacao => {
          console.log("recebendo notificacao especifica")
          this.notificacoes[0].push(notificacao)
          this.countNotificacoes = this.countNotificacoes + 1
        })


  }

  buscaNotificacaoInterval(){
    this.subscription = interval(300000).subscribe(() => {
      this.buscarNotificacoes();
      this.buscarNotificacoesPessoais();
    });
  }

  marcarComoLidaInterval(){
    this.subscription = interval(30000).subscribe(() => {
      this.salvarComolidas()  
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
          let length = this.notificacoes.filter((a: { lida: boolean; }) => a.lida==false).length
          if(this.countNotificacoes != length
            && length > this.countNotificacoes){
              this.countNotificacoes = this.countNotificacoes + (length - this.countNotificacoes)
          }
      })
  }

  buscarNotificacoesPessoais(){
    let email = jwt.jwtDecode(this.usuarioLogado.token).sub;
    this.notificacaoServices.buscarNotificacoesPessoais(email)
      .subscribe(res => {
        if(res){
          this.notificacoes.push(res)
          this.notificacoesPessoais.push(res)

          let length = this.notificacoesPessoais.filter((a: { lida: boolean; }) => a.lida==false).length
          if(this.countNotificacoesPessoais != length 
            && length > this.countNotificacoesPessoais){
              this.countNotificacoesPessoais = this.countNotificacoesPessoais + (length - this.countNotificacoesPessoais)
          }
        }
      })
  }

  marcarComoLida(notificacao:any){
    $(`.${notificacao.id}`).css("background-color", "white")
    let notificacaoLida = {
      id: notificacao.id,
      lida: true
    }
    this.notificacoesLidas.push(notificacaoLida)
  }

  salvarComolidas(){
    if(this.notificacoesLidas.length > 0){
      console.log(this.notificacoesLidas)
      this.notificacaoServices.salvarComoLidas(this.notificacoesLidas)
        .subscribe(res => {
          if(res)
            this.notificacoes.push(res)
            this.notificacoes = this.notificacoes[0]
            this.countNotificacoes = this.notificacoes.length
        })
    }
  }
}
