import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { CadastroService } from '../../../Services/Revisita/cadastro.service';

@Component({
  selector: 'app-revisita-cadastro',
  imports: [RouterOutlet, FormsModule],
  templateUrl: './revisita-cadastro.component.html',
  styleUrl: './revisita-cadastro.component.scss'
})
export class RevisitaCadastroComponent {

  constructor(private cadastroService: CadastroService){}

  revisita = {
    rua:null,
    bairro: null,
    numero: null,
    cidade: null,
    estado: null,
    cep:null,
    descricao: null,
    nome:null,
    telefone:null
  }

  salvar(){
    this.cadastroService.salvar(this.revisita)
      .subscribe(res => {
        
      })
  }

}
