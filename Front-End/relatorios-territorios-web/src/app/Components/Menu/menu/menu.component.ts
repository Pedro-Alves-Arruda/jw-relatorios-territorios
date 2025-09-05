import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from '../../../AuthService';

@Component({
  selector: 'app-menu',
  imports: [RouterOutlet, MatIconModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
    constructor(private router:Router, private authService:AuthService){}
  
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
