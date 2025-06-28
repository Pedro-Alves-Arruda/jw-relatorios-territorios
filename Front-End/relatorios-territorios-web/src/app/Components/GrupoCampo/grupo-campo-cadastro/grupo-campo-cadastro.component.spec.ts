import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrupoCampoCadastroComponent } from './grupo-campo-cadastro.component';

describe('GrupoCampoCadastroComponent', () => {
  let component: GrupoCampoCadastroComponent;
  let fixture: ComponentFixture<GrupoCampoCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrupoCampoCadastroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GrupoCampoCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
