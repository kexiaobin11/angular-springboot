import { TestBed } from '@angular/core/testing';

import { KlassService } from './klass.service';
import {HttpClientModule} from '@angular/common/http';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {Klass} from '../norm/entity/Klass';

describe('KlassService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ]
  }));

  it('should be created', () => {
    const service: KlassService = TestBed.get(KlassService);
    expect(service).toBeTruthy();
  });
  it('all', () => {
    const service: KlassService = TestBed.get(KlassService);
    let result;
    service.all().subscribe( (data) => {
      result = data;
    });
    const testingController: HttpTestingController = TestBed.get(HttpTestingController);
    const request = testingController.expectOne((req) => req.url === 'http://localhost:8080/Klass');
    expect(request.request.headers.has('name'));
    expect(request.request.method).toEqual('GET');
    const klasses = [new Klass(null, null, null)];
    request.flush(klasses);
    expect(result).toBe(klasses);
  });
});
