import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicadorCadastroComponent } from './publicador-cadastro.component';

describe('PublicadorCadastroComponent', () => {
  let component: PublicadorCadastroComponent;
  let fixture: ComponentFixture<PublicadorCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PublicadorCadastroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicadorCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
