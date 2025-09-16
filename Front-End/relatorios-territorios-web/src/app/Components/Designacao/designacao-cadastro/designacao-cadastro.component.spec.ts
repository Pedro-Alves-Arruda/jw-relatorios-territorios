import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DesignacaoCadastroComponent } from './designacao-cadastro.component';

describe('DesignacaoCadastroComponent', () => {
  let component: DesignacaoCadastroComponent;
  let fixture: ComponentFixture<DesignacaoCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DesignacaoCadastroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DesignacaoCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
