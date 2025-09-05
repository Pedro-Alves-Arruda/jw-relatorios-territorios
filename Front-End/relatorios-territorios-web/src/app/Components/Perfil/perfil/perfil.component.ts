import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PerfilService } from '../../../Services/Perfil/perfil.service';
import { AuthService } from '../../../AuthService';

@Component({
  selector: 'app-perfil',
  imports: [FormsModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.scss'
})
export class PerfilComponent {
  
  constructor(private perfilServices:PerfilService, private authService:AuthService){}
  publicador = {
    nome:'',
    email:'',
    cpf:'',
    endereco:'',
    telefone:'',
    congregacao: {
      nome:'',
      endereco:''
    },
    grupoCampo:{
      nome:'',
      endereco:''
    }
  }

  ngOnInit(){
    let email = this.authService.getUsuarioLogado().email;
    this.perfilServices.getPerfil(email)
    .subscribe(res => {
        this.publicador = res;
    })
  }
}
