import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { FormsModule } from '@angular/forms';
declare var $: any;


@Component({
  selector: 'app-cronometro',
  imports: [ CommonModule, FormsModule],
  templateUrl: './cronometro.component.html',
  styleUrl: './cronometro.component.scss'
})
export class CronometroComponent {
  private subscription!: Subscription;
  public tempo: number = 0; // tempo em segundos
  public rodando: boolean = false;
  hora:number = 0;
  minuto:number = 0;
  tempoFinal: string = "00:00:00";

  @ViewChild('publicacao', { static: false }) publicacao!: ElementRef;

  publicacoes = ['Revista Sentinela', 'Despertai!', 'Brochura - Ame as Pessoas'];
  listPublicacoesDeixadas: string[] = [];
  
  revisita = {
    descricao: null,
    publicacoesDeixadas: this.listPublicacoesDeixadas,
    tempo: this.tempo
  }

  editarTempo(hora:any) {
    const [hh, mm] = hora.split(":").map(Number);
    let tempoSegundos = (hh * 3600) + (mm * 60); 
    this.tempo = tempoSegundos;
    this.formatarTempo()
  }


  ngAfterViewInit() {
    $(this.publicacao.nativeElement).select2();
  }


  iniciar() {
    if (!this.rodando) {
      this.rodando = true;
      this.subscription = interval(1000).subscribe(() => {
        this.tempo++;
        this.formatarTempo();
      });
    }
  }

  salvar() {
    // Implemente a lógica de salvar relatório aqui
    // Exemplo: console.log('Relatório adicionado:', this.publicacaoSelecionada, this.revisita.descricao);
  }

  adicionar(){
   
  }
  
  pausar() {
    this.rodando = false;
    this.subscription?.unsubscribe();
  }

  resetar() {
    this.pausar();
    this.tempo = 0;
  }

  formatarTempo(){
    let segundos = this.tempo;

    if(segundos >= 59){
      this.minuto = this.minuto + 1;
      this.tempo = 0
    }
    
    if(this.minuto >= 59){
      this.hora = this.hora + 1;
      this.minuto = 0
      this.tempo = 0
    }

    this.tempoFinal =  `${this.pad(this.hora)}:${this.pad(this.minuto)}:${this.pad(segundos)}`;
  }

  private pad(valor: number): string {
    return valor < 10 ? '0' + valor : valor.toString();
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }
}
