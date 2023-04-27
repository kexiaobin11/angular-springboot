import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CacheService {
  /** 认证令牌
   * 刷新时候对authToken 进行缓存，当发送请求的时候带着缓存，第一次请求为空
   */
  private static authToken: string = sessionStorage.getItem('authToken');

  constructor() {
  }
  /*
  * @param 从响应体返回token
  * 并保存到缓存
  * 并设置sessionStorage的token
  * */
  static setAuthToken(token: string) {
    CacheService.authToken = token;
    sessionStorage.setItem('authToken', token);
  }

  static getAuthToken() {
    if (CacheService.authToken === null) {
      return '';
    }
    return CacheService.authToken;
  }
}
