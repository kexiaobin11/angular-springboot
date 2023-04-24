import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class AuthTokenInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('拦截到请求信息。请求地址：' + req.url +  '; 请求方法：' + req.method);
    const reqClone = req.clone({
      setHeaders: {'auth-token': '87141e2e-65e7-4219-8ee2-5c91d397eadc'}
    });
    return next.handle(reqClone);
  }

}
