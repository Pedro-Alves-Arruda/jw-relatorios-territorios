import { Component, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator'; 
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ListarService } from '../../../Services/Revisita/listar.service';
import { MatIconModule } from '@angular/material/icon';
import $ from 'jquery';
import { AtualizarService } from '../../../Services/Revisita/atualizar.service';
import { AuthService } from '../../../AuthService';
import * as jwt from 'jwt-decode';

@Component({
  selector: 'app-revisitas-listar',
  imports: [RouterOutlet, CommonModule, FormsModule,  MatTableModule, MatPaginatorModule, MatIconModule],
  templateUrl: './revisitas-listar.component.html',
  styleUrl: './revisitas-listar.component.scss'
})
export class RevisitasListarComponent {

  constructor(private authService: AuthService, private listarServices:ListarService, private atualizarServices:AtualizarService){}

  @ViewChild(MatPaginator) paginator!: MatPaginator;
    
  dataSource = new MatTableDataSource<any>();
    
  displayedColumns: string[] = [];

  ngOnInit(){
    this.displayedColumns = [
      'Nome',
      'Descricao',
      'created_at',
      'Rua',
      'Bairro',
      'Numero',
      'Cidade',
      'Estado',
      'CEP',
      'Telefone',
      'Acoes'];
      this.listar();

    $('#modalEdicao').on('hidden.bs.modal', () => {
      $(".modal-body .adicionar-retorno").empty();
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
   
  }

  ngOnDestroy(): void {
  
  }

  revisitas?:any

  isEdicao = false;
  isSalvar = false

  revisita = {
    id:null,
    nome:null,
    rua:null,
    bairro:null,
    numero:null,
    cidade:null,
    estado:null,
    cep:null,
    descricao:"",
    telefone:null,
    created_at:Date.now,
    retorno:""
  }

  listar(){
    let email = jwt.jwtDecode(this.authService.getUsuarioLogado().token).sub
    this.listarServices.listar(email)
      .subscribe(res=>{
        this.dataSource.data = res
      })
  }

  formataData(data: string): string {
    const date = new Date(data);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // mês começa do 0
    const year = date.getFullYear();

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}`;
  }

  parseDataFormatada(dataStr: any): String {
    let mes = dataStr[1].toString().length == 1 ? 0 + dataStr[1].toString() : dataStr[1]
    let dia = dataStr[2].toString().length == 1 ? 0 + dataStr[2].toString() : dataStr[2]
    let hora = dataStr[3].toString().length == 1 ? 0 + dataStr[3].toString() : dataStr[4]
    let minuto = dataStr[4].toString().length == 1 ? 0 + dataStr[3].toString() : dataStr[4]
    
    return `${dataStr[0]}-${mes}-${dia}T${hora}:${minuto}`;
  }

  carregaValor(revisitaEditar:any){
    this.isEdicao = true
    this.isSalvar = false
    this.revisita.id = revisitaEditar.id
    this.revisita.nome = revisitaEditar.nome
    this.revisita.descricao = revisitaEditar.descricao
    this.revisita.bairro = revisitaEditar.bairro
    this.revisita.created_at = Date.now
    this.revisita.cep = revisitaEditar.cep
    this.revisita.estado = revisitaEditar.estado
    this.revisita.numero = revisitaEditar.numero
    this.revisita.telefone = revisitaEditar.telefone
    this.revisita.rua = revisitaEditar.rua
    this.revisita.cidade = revisitaEditar.cidade
  }

  atualizarRevisita(){
  
    if(this.revisita.retorno != null && this.revisita.retorno != ""){
      this.revisita.descricao = this.revisita.descricao + "\n" + "Retorno: " + this.revisita.retorno;
    }

    this.atualizarServices.atualizar(this.revisita)
      .subscribe(res => {

      })
  }

}
