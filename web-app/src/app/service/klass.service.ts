import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Klass} from '../norm/entity/Klass';

@Injectable({
  providedIn: 'root'
})
export class KlassService {
  private url = '/Klass';
  constructor(private httpClient: HttpClient) { }

  /**
   * 获取所有班级
   */
  all(): Observable<Klass[]> {
    const httpParams = new HttpParams().append('name', '');
    return this.httpClient.get<Klass[]>(this.url, {params: httpParams});
  }
}
