import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';
import { ListarServiceService } from '../../../Services/Congregacao/listar-service.service';
import { coerceStringArray } from '@angular/cdk/coercion';
import { CommonModule } from '@angular/common';
import { Congregacao } from '../../../Models/Congregacao';
import { LoginServicesService } from '../../../Services/Login/login-services.service';
import { AuthService } from '../../../AuthService';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-login',
  imports: [RouterOutlet, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(private router:Router, private congregacaoService: ListarServiceService, private loginServices: LoginServicesService, private authService:AuthService){}
  private subscription!: Subscription;
  mensagemSucesso: boolean = false;

  usuario = {
    email:null,
    password: null,
    nome:null,
    congregacao:null,
    emailRedefinicao: null
  }

  congregacoes:Array<Congregacao> = []

  verificarLogin(){
    this.loginServices.login(this.usuario)
    .subscribe(res =>{
      if(res.token != " " && res.token != null){
        this.authService.login(res)
        this.router.navigate(['/'])
      }
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

  solicitarLinkRedefinicaoSenha(){
    
    this.usuario.email = this.usuario.emailRedefinicao;
    this.authService.login(this.usuario);
    this.usuario.email = null;

    this.loginServices.solicitarLinkRedefinicaoSenha(this.usuario.emailRedefinicao)
    .subscribe({
      next: (res:any)=> {
        if(res){
          this.subscription = interval(10000).subscribe(() => {
              this.mensagemSucesso = true;
              this.subscription.unsubscribe();
              this.mensagemSucesso = false;
          });
        }
      },
      error: (erro:any) =>{

      }
    })
  }

}
