import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioRedefinicaoSenhaComponent } from './formulario-redefinicao-senha.component';

describe('FormularioRedefinicaoSenhaComponent', () => {
  let component: FormularioRedefinicaoSenhaComponent;
  let fixture: ComponentFixture<FormularioRedefinicaoSenhaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioRedefinicaoSenhaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioRedefinicaoSenhaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
