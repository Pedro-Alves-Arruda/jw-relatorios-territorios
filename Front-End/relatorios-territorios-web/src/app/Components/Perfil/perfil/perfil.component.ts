import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PerfilService } from '../../../Services/Perfil/perfil.service';
import { AuthService } from '../../../AuthService';
import * as jwt from 'jwt-decode';
import { MatIconModule } from '@angular/material/icon';
import $ from 'jquery';

@Component({
  selector: 'app-perfil',
  imports: [FormsModule, MatIconModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.scss'
})
export class PerfilComponent {
  
  constructor(private perfilServices:PerfilService, private authService:AuthService){
    this.email = jwt.jwtDecode(this.authService.getUsuarioLogado().token).sub;
  }
  
  email?:any;
  booleanDadosPessoais:boolean = true;
  booleanGrupoCampo:boolean = true;
  booleanCongregacao:boolean = true;

  publicador:any = {
    nome:'',
    email:'',
    cpf:'',
    endereco:'',
    telefone:'',
  }

  congregacao:any = {
    nome:'',
    endereco:''
  }
  grupoCampo:any ={
    nome:'',
    endereco:''
  }

  ngOnInit(){
    this.perfilServices.getPerfil(this.email)
    .subscribe(res => {
        this.publicador = res;
        res.congregacao != null? this.congregacao.nome = res.congregacao.nome: '';
        res.congregacao != null? this.congregacao.endereco = res.congregacao.rua +" "+res.congregacao.bairro+" "+res.congregacao.cidade:'';
        res.grupoCampo != null? this.grupoCampo.nome = res.grupoCampo.nome : '';
        res.grupoCampo != null? this.grupoCampo.endereco = res.grupoCampo.endereco: '';
    })
  }

  habilitaEdicaoDadosPessoais(){
    this.booleanDadosPessoais = false;
    $('#checkDadosPessoais').attr('style','display:flex;'); 
  }
  
  habilitaEdicaoCongregacao(){
    this.booleanCongregacao = false;
    $('#checkCongregacao').attr('style','display:flex;');
  }
  
  habilitaEdicaoGrupoCampo(){
    this.booleanGrupoCampo = false;
    $('#checkGrupoCampo').attr('style','display:flex;');
  }

  atualizarDadosPessoais(){
    this.publicador['email'] = this.email
    this.publicador['congregacao'] = this.congregacao
    this.publicador['grupoCampo'] = this.grupoCampo

    this.perfilServices.atualizarPerfil(this.publicador)
      .subscribe(res => {

      })
  }
}
