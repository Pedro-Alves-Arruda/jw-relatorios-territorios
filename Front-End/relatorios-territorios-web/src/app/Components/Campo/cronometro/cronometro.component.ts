import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { FormsModule } from '@angular/forms';

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

  publicacoes = ['Revista Sentinela', 'Despertai!', 'Brochura - Ame as Pessoas'];
  publicacaoSelecionada?: string;
  listPublicacoesDeixadas: string[] = [];
  
  revisita = {
    descricao: null,
    publicacoesDeixadas: this.listPublicacoesDeixadas,
    tempo: this.tempo
  }
  
  iniciar() {
    if (!this.rodando) {
      this.rodando = true;
      this.subscription = interval(1000).subscribe(() => {
        this.tempo++;
      });
    }
  }

  salvar() {
    // Implemente a lógica de salvar relatório aqui
    // Exemplo: console.log('Relatório adicionado:', this.publicacaoSelecionada, this.revisita.descricao);
  }

  adicionar(){
    if(this.publicacaoSelecionada){
        this.listPublicacoesDeixadas.push(this.publicacaoSelecionada);
        this.publicacaoSelecionada = undefined;
    }
  }
  
  pausar() {
    this.rodando = false;
    this.subscription?.unsubscribe();
  }

  resetar() {
    this.pausar();
    this.tempo = 0;
  }

  formatarTempo(): string {
    const minutos = Math.floor(this.tempo / 60);
    const segundos = this.tempo % 60;
    return `${this.pad(minutos)}:${this.pad(segundos)}`;
  }

  private pad(valor: number): string {
    return valor < 10 ? '0' + valor : valor.toString();
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }
}
