import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

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
  /**
   * 传一个true过来设置，this.isLoginCacheKey值是为isLogin，value不等于‘1’
   * 先设置默认值，默认的isLogin是false，convertStringToBoolean进行转化
   * 订阅的是是false*
   * * */
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
}
