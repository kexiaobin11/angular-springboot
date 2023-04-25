import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {CacheService} from '../service/cache.service';

@Injectable()
export class AuthTokenInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('拦截到请求信息。请求地址：' + req.url +  '; 请求方法：' + req.method);
    console.log('获得请求的token' + CacheService.getAuthToken());
    const reqClone = req.clone({
      setHeaders: {'auth-token': CacheService.getAuthToken()}
  });
    return next.handle(reqClone).pipe(map((httpEvent) => {
      if (httpEvent instanceof HttpResponse) {
        const httpResponse = httpEvent as HttpResponse<any>;
        const authToken = httpResponse.headers.get('auth-token');
        CacheService.setAuthToken(authToken);
      }
      return httpEvent;
    }));
  }

}
