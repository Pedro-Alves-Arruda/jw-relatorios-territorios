import { TestBed } from '@angular/core/testing';

import { GrupoCampoService } from './grupo-campo.service';

describe('GrupoCampoService', () => {
  let service: GrupoCampoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoCampoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
