import { ChangeDetectorRef, Component, Input, input, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from '../../../AuthService';
import $ from 'jquery';
import { WebSocketService } from '../../../Services/WebSocket/web-socket.service';
import { CommonModule } from '@angular/common';
import { NotificacoesService } from '../../../Services/Notificacoes/notificacoes.service';
import { interval, Subscription } from 'rxjs';
import * as jwt from 'jwt-decode'
import { Notificacao } from '../../../Models/Notificacao';
import { NgChartsModule } from 'ng2-charts';
import { NgxEchartsModule } from 'ngx-echarts';
import { CadastroService } from '../../../Services/Publicador/cadastro.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator'; 
import { ListarService } from '../../../Services/Revisita/listar.service';



@Component({
  selector: 'app-menu',
  imports: [RouterOutlet, MatIconModule, CommonModule, NgChartsModule, NgxEchartsModule, MatTableModule, MatPaginatorModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  constructor(private router:Router, 
    private authService:AuthService, 
    private webSocketServices:WebSocketService,
    private notificacaoServices:NotificacoesService,
    private cdr:ChangeDetectorRef,
    private publicadorServices:CadastroService,
    private listarServices:ListarService){}

  notificacoes:Notificacao[] = []
  notificacoesPessoais:any[] = []
  private subscription!: Subscription;
  countTotalNotificacoes:any = 0
  countNotificacoes:any = 0
  countNotificacoesPessoais:any = 0
  notificacoesLidas: any[] = []
  usuarioLogado:any
  dadosGraficoPizza:any
  data?:any
  mostraGraficos:boolean = false
  revisitas?:any
  @ViewChild(MatPaginator) paginator!: MatPaginator;
    
  dataSource = new MatTableDataSource<any>();
    
  displayedColumns:any = [
        'Nome',
        'Descricao',
        'created_at',
        'Rua',
        'Bairro',
        'Numero',
        'Cidade',
        'Estado',
        'CEP',
        'Telefone'];

  pieChartOptions:any = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'rigth'
      },
      color: ['rgba(105, 36, 124, 0.735)', 'white', '#9f9f9fff',],
      series: [
        {
          type: 'pie',
          radius: '60%',
          data: this.data,
           label: {
            show: false 
          },
          labelLine: {
            show: false
          },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
  };

  lineChartOptions: any = {
    title: {
      text: 'Revisitas Mensais',
      textStyle: {
        color: 'white'
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: []
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '',
        type: 'line',
        smooth: true,
        data: [],
        color:['rgba(105, 36, 124, 0.735)'] 
      },
    ]
  };
  
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  ngOnInit(){
    this.usuarioLogado = this.authService.getUsuarioLogado();
    this.buscarNotificacoes();
    this.buscarNotificacoesPessoais();
    this.buscaNotificacaoInterval()
    this.marcarComoLidaInterval()

    if(this.router.url == '/'){
      this.buscarDadosGrafico();
      this.listar();
    }

      this.webSocketServices.notificacaoSubject
        .subscribe(notificacao => {
          
          this.notificacoes.push(notificacao)
          this.countNotificacoes = this.countNotificacoes + 1
        })
        
        this.webSocketServices.notificacaoPessoalSubject
        .subscribe(notificacao => {
        
          this.notificacoes.push(notificacao)
          this.countNotificacoes = this.countNotificacoes + 1
        })
  }





  listar(){
    let email = jwt.jwtDecode(this.authService.getUsuarioLogado().token).sub
    this.listarServices.listar(email)
      .subscribe(res=>{
        this.dataSource.data = res
      })
  }

  buscarDadosGrafico(){
    let email = jwt.jwtDecode(this.usuarioLogado.token).sub

    this.publicadorServices.buscarDadosGrafico(email)
      .subscribe(res => {
        if(res){
          this.montaGraficoPizza(res)
          this.montaGraficoLinha(res)
          this.mostraGraficos = true
        }
      })
  }

  montaGraficoPizza(res:any){
    this.pieChartOptions = {
      ...this.pieChartOptions,
      series: [
        {
          ...(this.pieChartOptions.series?.[0] || {}),
          data: [
              { value: res.graficoPizzaDTO.numeroRevisitas, name: 'N de Revisitas' },
              { value: res.graficoPizzaDTO.numeroEstudoBiblico, name: 'N de Estudos' },
              { value: res.graficoPizzaDTO.numeroServicoCampo, name: 'N de Serviços de Campo'}
            ]
          
        }
      ]
    };
  }

  montaGraficoLinha(res:any){
    let meses: any[] = [];
    let data: any[] = []
    res.graficoLinhaDTOS.forEach((a: { mes: any; quantidade:any}) => {
      meses.push(a.mes)
      data.push(a.quantidade)
    })

    this.lineChartOptions = {
      ...this.lineChartOptions,
      ...(this.lineChartOptions.xAxis?.[0] || {}),
      xAxis:{
        type: 'category',
        data: meses
      },
      ...(this.lineChartOptions.series?.[0] || {}),
        series: [
        {
          name: 'Revisitas',
          type: 'line',
          smooth: true,
          data: data
        },
      ]
    }
  }

  buscaNotificacaoInterval(){
    this.subscription = interval(300000).subscribe(() => {
      this.buscarNotificacoes();
      this.buscarNotificacoesPessoais();
    });
  }

  marcarComoLidaInterval(){
    this.subscription = interval(30000).subscribe(() => {
      this.salvarComolidas()  
    });
  }

  redireciona(){
    if(this.authService.getUsuarioLogado().token != null)
      this.router.navigate(["/perfil"])
    else
      this.router.navigate(["/login"])
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/login'])
  }

  formataDataHora(dataHora:any){
    const date = new Date(dataHora);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); 
    const year = date.getFullYear();

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    if(day == new Date().getDate().toString())
      return `${hours}:${minutes}`;
    else
      return `${day}/${month}/${year} ${hours}:${minutes}`;
  }

  buscarNotificacoes(){
    this.notificacaoServices.buscarNotificacoes()
    .subscribe(res => {
      if(res)
        res.forEach(not => {
          this.notificacoes.push(not)
        })
        
        let length = this.notificacoes.filter((a: { lida: boolean; }) => a.lida == false).length

        if(this.countNotificacoes != length
            && length > this.countNotificacoes){
              this.countNotificacoes = length
              this.cdr.detectChanges();
          }
      })
  }

  buscarNotificacoesPessoais(){
    let email = jwt.jwtDecode(this.usuarioLogado.token).sub;
    this.notificacaoServices.buscarNotificacoesPessoais(email)
      .subscribe(res => {
        if(res){
          res.forEach(not => {
            this.notificacoes.push(not)
          })
         
          let length = this.notificacoes.filter((a: { lida: boolean; }) => a.lida == false).length

          if(this.countNotificacoes != length
            && length > this.countNotificacoes){
            this.countNotificacoes = length
            this.cdr.detectChanges();
          }
        }
      })
  }

  marcarComoLida(notificacao:any){
    $(`.${notificacao.id}`).css("background-color", "white")
    let notificacaoLida = {
      id: notificacao.id,
      lida: true
    }
    this.notificacoesLidas.push(notificacaoLida)
  }

  salvarComolidas(){
    if(this.notificacoesLidas.length > 0){
      this.notificacaoServices.salvarComoLidas(this.notificacoesLidas)
        .subscribe(res => {
          if(res)
            res.forEach(not => {
              this.notificacoes.push(not)
            })
        
            let length = this.notificacoes.filter((a: { lida: boolean; }) => a.lida == false).length

            if(this.countNotificacoes != length
                && length > this.countNotificacoes){
                  this.countNotificacoes = length
                  this.cdr.detectChanges();
              }
        })
    }
  }
}
