import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ServicoCampoService } from '../../../Services/Campo/servico-campo.service';
import { AuthService } from '../../../AuthService';
declare var $: any;


@Component({
  selector: 'app-cronometro',
  imports: [ CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './cronometro.component.html',
  styleUrl: './cronometro.component.scss'
})
export class CronometroComponent {

  constructor(private servicesCampo:ServicoCampoService, private authService:AuthService){}

  private subscription!: Subscription;
  public tempo: number = 0; // tempo em segundos
  public rodando: boolean = false;
  hora:number = 0;
  minuto:number = 0;
  tempoFinal: string = "00:00:00";

  @ViewChild('publicacao', { static: false }) publicacao!: ElementRef;

  publicacoes:string[] = ['Sentinela - o fim das guerras, como?', 
                'Video - Por que estudar a Bíblia?',
                'Video - como é um estudo da Bíblia?',
                'Video - O que acontece em um salão do reino?',
                'Brochura - Seja feliz para sempre', 
                'Brochura - Ame as Pessoas',
                'Livro - Seja feliz para sempre',];
  
  listPublicacoesDeixadas:string[] = [] ;
  publicacaoControl = new FormControl([]);
  publicadorLogado:any;

  servicoCampo:any = {
    descricao: '',
    tempo: ''
  }

  ngOnInit(){
    this.publicacaoControl.valueChanges.subscribe(val => {
      console.log('Selecionados:', val);
    });
    this.publicadorLogado = this.authService.getUsuarioLogado();
  }

  editarTempo(hora:any) {
    const [hh, mm] = hora.split(":").map(Number);
    this.tempoFinal =  `${this.pad(hh)}:${this.pad(mm)}:${this.pad(0)}`;
  }


  ngAfterViewInit() {
    const select = $(this.publicacao.nativeElement).select2();
    
    select.on('change', (e: any) => {
      let values = $(e.target).val();
      
      if (JSON.stringify(this.publicacaoControl.value) !== JSON.stringify(values)) {
        let values = $(e.target).val();
        this.publicacaoControl.setValue(values);
      }
      
    });

    this.publicacaoControl.valueChanges.subscribe(val => {
      const current = select.val();
      if (JSON.stringify(current) != JSON.stringify(val)) {
        select.val(val).trigger('change.select2');
      }
    });
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
    this.servicoCampo.tempo = this.tempoFinal;
    this.servicoCampo['publicacoesDeixadas'] =  this.publicacaoControl.value;
    this.servicoCampo['email'] = this.publicadorLogado.email;
    this.servicesCampo.salvarServicoCampo(this.servicoCampo)
      .subscribe({
        next: (sucess) =>{

        },
        error: (error) => {

        }
      }) 
  }

  adicionar(value:any){
    this.listPublicacoesDeixadas.push(value);
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

    if(segundos >= 60){
      this.minuto = this.minuto + 1;
      this.tempo = 0
    }
    
    if(this.minuto >= 60){
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
