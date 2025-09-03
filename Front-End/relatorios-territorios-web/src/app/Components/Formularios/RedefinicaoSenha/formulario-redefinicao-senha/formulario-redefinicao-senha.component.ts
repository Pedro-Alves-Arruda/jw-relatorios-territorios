import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginServicesService } from '../../../../Services/Login/login-services.service';
import { AuthService } from '../../../../AuthService';

@Component({
  selector: 'app-formulario-redefinicao-senha',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './formulario-redefinicao-senha.component.html',
  styleUrl: './formulario-redefinicao-senha.component.scss'
})
export class FormularioRedefinicaoSenhaComponent {

  constructor(private loginServices:LoginServicesService, private authsService:AuthService){}

  buttonAbilitado:boolean = false
  
  senhasNovas = {
    password:"",
    senha2:"",
    email:null
  }

    salvarSenhaNova(){
    if(this.validarSenhas()){

      let usuario =  this.authsService.getUsuarioLogado();
      this.senhasNovas.email = usuario.email;

      this.loginServices.salvarSenhaNova(this.senhasNovas)
        .subscribe({
          next: (sucess) => {
            alert("Senha alterada com sucesso!")
          },
          error: (err) =>{
            alert("Erro ao alterar senha, tente novamente mais tarde.")
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
