import { TestBed } from '@angular/core/testing';

import { SiblingInformationService } from './sibling-information.service';

describe('SiblingInformationService', () => {
  let service: SiblingInformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SiblingInformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
