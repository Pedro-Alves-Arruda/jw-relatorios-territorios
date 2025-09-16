import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PerfilService } from '../../../Services/Perfil/perfil.service';
import { AuthService } from '../../../AuthService';
import * as jwt from 'jwt-decode';
import { MatIconModule } from '@angular/material/icon';
import $ from 'jquery';
import { CommonModule } from '@angular/common';
import { Token } from '../../../Models/Token';

@Component({
  selector: 'app-perfil',
  imports: [FormsModule, MatIconModule, CommonModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.scss'
})
export class PerfilComponent {
  
  constructor(private perfilServices:PerfilService, private authService:AuthService){
    
  }
  
  email?:any;
  booleanDadosPessoais:boolean = true;
  booleanGrupoCampo:boolean = true;
  booleanCongregacao:boolean = true;
  booleanNomeCpf:boolean = true;
  mostraImagem:boolean = false
  imagePreview:any
  fotoPerfil:any = {
    imagem:'',
  }

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
    this.email = jwt.jwtDecode<Token>(this.authService.getUsuarioLogado().token).sub;
    this.getPerfil();
    this.getFotoPerfil();
  }
  
  getPerfil(){
    this.perfilServices.getPerfil(this.email)
    .subscribe(res => {
        this.publicador = res;
        res.congregacao != null? this.congregacao.nome = res.congregacao.nome: '';
        res.congregacao != null? this.congregacao.endereco = res.congregacao.rua +" "+res.congregacao.bairro+" "+res.congregacao.cidade:'';
        res.grupoCampo != null? this.grupoCampo.nome = res.grupoCampo.nome : '';
        res.grupoCampo != null? this.grupoCampo.endereco = res.grupoCampo.endereco: '';
    })
  }
  
  getFotoPerfil(){
    this.perfilServices.getFotoPerfil(this.email)
      .subscribe(res => {
        if(res.imagem != "" || res.imagem != null){
          $(".mat-foto-perfil").css("display", "none")
          this.imagePreview = res.imagem
          this.mostraImagem = true
        }
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

  habilitaEdicaoNomeCpf(){
    $('#checkNomeCpf').attr('style','display:flex;'); 
    this.booleanNomeCpf = false
  }

  atualizarDadosPessoais(){
    this.publicador['email'] = this.email
    this.publicador['congregacao'] = this.congregacao
    this.publicador['grupoCampo'] = this.grupoCampo

    this.perfilServices.atualizarPerfil(this.publicador)
      .subscribe(res => {

      })
  }

  onFileSelected(event:any){
    const file: File = event.target.files[0];
    if (file) {
      $(".mat-foto-perfil").css("display", "none")
      
      const reader = new FileReader();
      
      reader.onload = () => {
        const base64String = reader.result as string;
        this.imagePreview = reader.result as string
        this.mostraImagem = true
        this.fotoPerfil.imagem = base64String
        this.fotoPerfil['email'] = this.email
        this.perfilServices.salvarImagemPerfil(this.fotoPerfil)
          .subscribe(res => {})
      };
      reader.readAsDataURL(file);
    }
  }
}
