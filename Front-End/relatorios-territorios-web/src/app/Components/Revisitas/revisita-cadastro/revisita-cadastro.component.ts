import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-revisita-cadastro',
  imports: [RouterOutlet, FormsModule],
  templateUrl: './revisita-cadastro.component.html',
  styleUrl: './revisita-cadastro.component.scss'
})
export class RevisitaCadastroComponent {

  revisita = {
    rua:null,
    bairro: null,
    numero: null,
    cidade: null,
    estado: null,
    cep:null,
    descricao: null
  }

  salvar(){
    
  }

}
