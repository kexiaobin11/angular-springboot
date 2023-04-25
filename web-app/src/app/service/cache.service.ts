import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CacheService {
  /** 认证令牌 */
  private static authToken: string = undefined;

  constructor() {
  }

  static setAuthToken(token: string) {
    CacheService.authToken = token;
  }

  static getAuthToken() {
    if (CacheService.authToken === undefined) {
      return '';
    }
    return CacheService.authToken;
  }
}
