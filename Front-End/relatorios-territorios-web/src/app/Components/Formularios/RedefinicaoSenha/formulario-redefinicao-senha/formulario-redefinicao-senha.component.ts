import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginServicesService } from '../../../../Services/Login/login-services.service';
import { AuthService } from '../../../../AuthService';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import * as jwt from 'jwt-decode'

@Component({
  selector: 'app-formulario-redefinicao-senha',
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './formulario-redefinicao-senha.component.html',
  styleUrl: './formulario-redefinicao-senha.component.scss'
})
export class FormularioRedefinicaoSenhaComponent {

  constructor(private loginServices:LoginServicesService, private route: ActivatedRoute){}

  buttonAbilitado:boolean = false
  mensagemSucesso:boolean = false
  mensagemErro:boolean = false
  mostraFormulario:boolean = true;
  
  senhasNovas:any = {
    password:"",
    senha2:""
  }

  ngOnInit(){
    let token = this.route.snapshot.queryParamMap.get("char");
    if(!this.isTokenExpired(token) && token){
      this.senhasNovas['email'] = jwt.jwtDecode(token).sub
    }else{
      this.mostraFormulario=false;
      alert("Formulario para redefinição de senha expirado, por favor solicite um novo")
    }
  }

  isTokenExpired(token:any){
    try{
        const decode: any = jwt.jwtDecode(token)
        const now = Date.now().valueOf() / 1000;
        return decode.exp < now
    }catch (err){
        return true;
    }
  }

  salvarSenhaNova(){
    if(this.validarSenhas()){
      this.loginServices.salvarSenhaNova(this.senhasNovas)
        .subscribe(res => {
          if(res.toString()){
            this.mensagemSucesso = true;
            setTimeout(() => {
              this.mensagemSucesso = false;
            }, 10000);
          }else{
            this.mensagemErro = true;
            setTimeout(() => {
              this.mensagemErro = false;
            }, 10000);
          }
        })
    }else{
      alert("As senhas não coincidem ou não atendem aos requisitos abaixos:"
            +"\n*Mínimo de 4 caracteres"
            +"\n*No mínimo 1 letra maiúscula"
            +"\n*No mínimo 1 letra numero"
            +"\n*No mínimo 1 caracter especial ( ! @ # $ % ^ & * ))")
    }
  }

  validarSenhas():boolean{
    const regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{4,}$/;
    if(this.senhasNovas.password == this.senhasNovas.senha2 && regex.test(this.senhasNovas.password)){
      this.buttonAbilitado = false;
      return true;
    }else{
      this.buttonAbilitado = true;
      return false;
    }
  }

}
