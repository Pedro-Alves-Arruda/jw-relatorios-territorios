import { TestBed } from '@angular/core/testing';

import { DesignacaoServicesService } from './designacao-services.service';

describe('DesignacaoServicesService', () => {
  let service: DesignacaoServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DesignacaoServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
