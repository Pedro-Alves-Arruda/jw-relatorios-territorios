import { TestBed } from '@angular/core/testing';

import { ServicoCampoService } from './servico-campo.service';

describe('ServicoCampoService', () => {
  let service: ServicoCampoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoCampoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
