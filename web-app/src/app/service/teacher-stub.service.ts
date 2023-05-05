import {Observable, of} from 'rxjs';
import {Teacher} from '../norm/entity/Teacher';


export class TeacherStubService {
  setIsLogin(isLogin: boolean): void {
    return;
  }
  constructor() { }
  login(username: string, password: string): Observable<boolean> {
    return null;
  }
  me(): Observable<Teacher> {
    return of(new Teacher(null, null, null));
  }
  logout(): Observable<void> {
    return of(null);
  }
}
