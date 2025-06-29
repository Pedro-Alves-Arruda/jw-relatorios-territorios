import { TestBed } from '@angular/core/testing';

import { ListarServiceService } from './listar-service.service';

describe('ListarServiceService', () => {
  let service: ListarServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListarServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
