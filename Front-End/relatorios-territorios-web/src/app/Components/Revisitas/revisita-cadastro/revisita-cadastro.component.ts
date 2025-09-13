import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';
import { CadastroService } from '../../../Services/Revisita/cadastro.service';
import * as jwt from 'jwt-decode';
import { AuthService } from '../../../AuthService';

@Component({
  selector: 'app-revisita-cadastro',
  imports: [RouterOutlet, FormsModule],
  templateUrl: './revisita-cadastro.component.html',
  styleUrl: './revisita-cadastro.component.scss'
})
export class RevisitaCadastroComponent {

  constructor(private cadastroService: CadastroService, private authService:AuthService, private route:Router){}

  revisita:any = {
    rua:null,
    bairro: null,
    numero: null,
    cidade: null,
    estado: null,
    cep:null,
    descricao: null,
    nome:null,
    telefone:null,
  }

  salvar(){
    this.revisita['email'] = jwt.jwtDecode(this.authService.getUsuarioLogado().token).sub;
    this.cadastroService.salvar(this.revisita)
      .subscribe(res => {
        this.route.navigate(['/revisita-listar'])
      })
  }

}
