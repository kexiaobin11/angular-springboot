import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Teacher} from '../norm/entity/Teacher';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {
  private isLogin;
  public isLogin$;
  private isLoginCacheKey = 'isLogin';
  setIsLogin(isLogin: boolean) {
    window.sessionStorage.setItem(this.isLoginCacheKey, this.convertBooleanToString(isLogin));
    this.isLogin.next(isLogin);
  }

  constructor(private httpClient: HttpClient) {
    const isLogin: string = window.sessionStorage.getItem(this.isLoginCacheKey);
    this.isLogin = new BehaviorSubject(this.convertStringToBoolean(isLogin));
    this.isLogin$ = this.isLogin.asObservable();
  }

  convertStringToBoolean(value: string) {
  return value === '1';
  }


  convertBooleanToString(value: boolean) {
  return value ? '1' : '0';
  }


  login(username: string, password: string): Observable<boolean> {
    const url = 'http://localhost:8080/Teacher/login';
    return this.httpClient.post<boolean>(url, {username, password});
  }


  me(): Observable<Teacher> {
    const url = 'http://localhost:8080/Teacher/me';
    return this.httpClient.get<Teacher>(url);
  }

  /*
  * 注销方法
  * */
  logout(): Observable<void> {
    const url = 'http://localhost:8080/Teacher/logout';
    return this.httpClient.get<void>(url);
  }
}
