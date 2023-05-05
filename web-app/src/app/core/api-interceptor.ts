import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiInterceptor implements HttpInterceptor {
  private static api = 'http://localhost:8080';

  private static getUrl(url: string): string {
    if (url.startsWith('/')) {
      return this.api + url;
    } else if (url.startsWith('http')) {
      return url;
    } else {
      return this.api + '/' + url;
    }
  }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const url = ApiInterceptor.getUrl(request.url);
    // 克隆一个req出来
    const req = request.clone({url});
    return next.handle(req);
  }
}
