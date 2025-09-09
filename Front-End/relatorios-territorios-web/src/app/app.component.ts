import { Component, HostListener } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuComponent } from "./Components/Menu/menu/menu.component";
import { WebSocketService } from './Services/WebSocket/web-socket.service';
import { NotificacoesService } from './Services/Notificacoes/notificacoes.service';
import { AuthService } from './AuthService';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  isMobile = window.innerWidth <= 768;

  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    this.isMobile = window.innerWidth <= 768;
  }
  title = 'relatorios-territorios-web';

  constructor(){}
  ngOnInit(){}

}
