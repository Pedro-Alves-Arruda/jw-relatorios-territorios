import { TestBed } from '@angular/core/testing';

import { AtualizarService } from './atualizar.service';

describe('AtualizarService', () => {
  let service: AtualizarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtualizarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
