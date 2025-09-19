import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CadastroService } from '../../../Services/Publicador/cadastro.service';
import { DesignacaoServicesService } from '../../../Services/Designacao/designacao-services.service';

@Component({
  selector: 'app-designacao-cadastro',
  imports: [CommonModule, FormsModule],
  templateUrl: './designacao-cadastro.component.html',
  styleUrl: './designacao-cadastro.component.scss'
})
export class DesignacaoCadastroComponent {

  publicadores:any
  designacoes:any
  designacao = {
    email:'',
    dia:null,
    designacao:''
  }
  mensagemSucesso:boolean = false
  mensagemErro:boolean = false

  constructor(private publicadorServices:CadastroService, private designacoesServices:DesignacaoServicesService){}

  ngOnInit(){
    this.listarPublicadores();
    this.listarDesignacoes();
  }

  listarPublicadores(){
    this.publicadorServices.listar()
      .subscribe(res => {
        if(res){
          this.publicadores = res
        }
      })
  }

  listarDesignacoes(){
    this.publicadorServices.listarDesignacoes()
      .subscribe(res => {
        if(res){
          this.designacoes = res
        }
      })
  }

  salvar(){
    console.log(this.designacao)
    this.designacoesServices.nova(this.designacao)
      .subscribe(res => {
        if(res){
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
  }

}
