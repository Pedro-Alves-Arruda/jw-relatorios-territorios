import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevisitasListarComponent } from './revisitas-listar.component';

describe('RevisitasListarComponent', () => {
  let component: RevisitasListarComponent;
  let fixture: ComponentFixture<RevisitasListarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RevisitasListarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevisitasListarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
