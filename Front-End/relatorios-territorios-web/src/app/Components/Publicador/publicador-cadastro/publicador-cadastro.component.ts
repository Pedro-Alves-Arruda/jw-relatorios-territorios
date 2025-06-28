
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CadastroService } from '../../../Services/Publicador/cadastro.service';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-publicador-cadastro',
  imports: [FormsModule, RouterOutlet],
  templateUrl: './publicador-cadastro.component.html',
  styleUrl: './publicador-cadastro.component.scss'
})
export class PublicadorCadastroComponent {

  constructor(private cadastroPublicador: CadastroService){}

  publicador = {
    nome : null,
    cpf:null,
    telefone: null,
    email:null,
    congregacao:null,
    grupoCampo:null,
    funcao:null
  }

  salvar(){
    this.cadastroPublicador.salvar(this.publicador)
    .subscribe(res => {
      console.log(res)
    })
  }

}
