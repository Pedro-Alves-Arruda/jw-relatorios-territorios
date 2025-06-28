import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-grupo-campo-cadastro',
  imports: [FormsModule, RouterOutlet],
  templateUrl: './grupo-campo-cadastro.component.html',
  styleUrl: './grupo-campo-cadastro.component.scss'
})
export class GrupoCampoCadastroComponent {

  grupoCampo = {
    responsavel: null,
    ajudante: null,
    rua: null,
    numero: null,
    bairro: null,
    cep: null    
  }

  salvar(){

  }
}
