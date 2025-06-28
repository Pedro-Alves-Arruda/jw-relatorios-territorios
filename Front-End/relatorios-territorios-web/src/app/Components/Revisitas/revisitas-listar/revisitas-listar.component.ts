import { Component, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator'; 
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-revisitas-listar',
  imports: [RouterOutlet, CommonModule, FormsModule,  MatTableModule, MatPaginatorModule],
  templateUrl: './revisitas-listar.component.html',
  styleUrl: './revisitas-listar.component.scss'
})
export class RevisitasListarComponent {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
    
  dataSource = new MatTableDataSource<any>();
    
  displayedColumns: string[] = [];

  ngOnInit(){
    this.displayedColumns = ['Nome','Descricao', 'Endereço', "numeroVisitas"];
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  isEdicao = false;
  isSalvar = false

  revisita = {
    rua:null,
    bairro:null,
    numero:null,
    descricao:null,
    cep:null,
    estado:null,
    cidade:null
  }

}
