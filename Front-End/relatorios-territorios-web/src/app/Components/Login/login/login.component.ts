import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { ListarServiceService } from '../../../Services/Congregacao/listar-service.service';
import { coerceStringArray } from '@angular/cdk/coercion';
import { CommonModule } from '@angular/common';
import { Congregacao } from '../../../Models/Congregacao';
import { LoginServicesService } from '../../../Services/Login/login-services.service';
import { AuthService } from '../../../AuthService';

@Component({
  selector: 'app-login',
  imports: [RouterOutlet, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(private congregacaoService: ListarServiceService, private loginServices: LoginServicesService, private authService:AuthService){}

  usuario = {
    email:null,
    password: null,
    nome:null,
    congregacao:null
  }

  congregacoes:Array<Congregacao> = []

  verificarLogin(){
    this.loginServices.login(this.usuario)
    .subscribe(res => {
      this.authService.login(res)
    })

  }

  solicitarLink(){

  }

  preparaDadosModal(){
    this.congregacaoService.listar()
    .subscribe(res => {
      if(res != null)this.congregacoes = res
    })
  }

}
