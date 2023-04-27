import { Injectable } from '@angular/core';
import {Klass} from '../norm/entity/Klass';
import {Observable} from 'rxjs';

export class KlassStubService {

  constructor() { }
  all(): Observable<Klass[]> {
    return null;
  }
}
