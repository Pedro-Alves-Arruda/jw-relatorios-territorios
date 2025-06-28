import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevisitaCadastroComponent } from './revisita-cadastro.component';

describe('RevisitaCadastroComponent', () => {
  let component: RevisitaCadastroComponent;
  let fixture: ComponentFixture<RevisitaCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RevisitaCadastroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevisitaCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
