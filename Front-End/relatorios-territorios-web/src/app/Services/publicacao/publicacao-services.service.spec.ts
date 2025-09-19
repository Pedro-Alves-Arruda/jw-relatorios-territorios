import { TestBed } from '@angular/core/testing';

import { PublicacaoServicesService } from './publicacao-services.service';

describe('PublicacaoServicesService', () => {
  let service: PublicacaoServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PublicacaoServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
