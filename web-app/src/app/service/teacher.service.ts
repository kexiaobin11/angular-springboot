import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Teacher} from '../norm/entity/Teacher';

@Injectable({
  providedIn: 'root'
})
  /*
  * isLogin#表示可以被订阅，给appComponent进行订阅
  * this.isLogin.next(true)的同时，给订阅者返回true
  * */
export class TeacherService {
  private isLogin;
  public  isLogin$;
  private isLoginCacheKey = 'isLogin';
  setIsLogin(isLogin: boolean) {
    window.sessionStorage.setItem(this.isLoginCacheKey, this.convertBooleanToString(isLogin));
    this.isLogin.next(isLogin);
  }
    /*
    * 默认isLogin 是false
    * */
  constructor(private httpClient: HttpClient) {
    const isLogin: string = window.sessionStorage.getItem(this.isLoginCacheKey);
    this.isLogin = new BehaviorSubject(this.convertStringToBoolean(isLogin));
    this.isLogin$ = this.isLogin.asObservable();
  }
  /*
    * 字符转换成布尔值
    * */
  convertStringToBoolean(value: string) {
  return value === '1';
  }

  /*
  * 布尔值转换成字符
  * */
  convertBooleanToString(value: boolean) {
  return value ? '1' : '0';
  }

  /*
  * 登陆
  * @param username，password
  * */
  login(username: string, password: string): Observable<boolean> {
    const url = '/Teacher/login';
    return this.httpClient.post<boolean>(url, {username, password});
  }

  /*
  * 个人中心
  * */
  me(): Observable<Teacher> {
    const url = '/Teacher/me';
    return this.httpClient.get<Teacher>(url);
  }

  /*
  * 注销方法
  * */
  logout(): Observable<void> {
    const url = 'http://localhost:8080/Teacher/logout';
    return this.httpClient.get<void>(url);
  }
  delete(id: number): Observable<void> {
    const url = 'Teacher/' + id;
    return this.httpClient.delete<void>(url);
  }
}
