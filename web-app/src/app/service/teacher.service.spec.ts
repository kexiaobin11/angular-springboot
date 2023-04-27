import {async, TestBed} from '@angular/core/testing';

import { TeacherService } from './teacher.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Teacher} from '../norm/entity/Teacher';

describe('TeacherService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      ReactiveFormsModule,
      HttpClientTestingModule
    ]
  }));
  it('should be created', () => {
    const service: TeacherService = TestBed.get(TeacherService);
    expect(service).toBeTruthy();
  });
  it('login', () => {
    const service: TeacherService = TestBed.get(TeacherService);
    expect(service).toBeTruthy();
    let result: boolean;
    service.login('username', 'password').subscribe( value => {
      result = value;
    });
    const httpTestingController: HttpTestingController = TestBed.get(HttpTestingController);
    const  req = httpTestingController.expectOne('http://localhost:8080/Teacher/login');
    expect(req.request.method).toEqual('POST');
    const usernameAndPassword: {username: string, password: string} = req.request.body.value;
    expect(usernameAndPassword.username).toEqual('username');
    expect(usernameAndPassword.password).toEqual('password');
    req.flush('true');
    expect(result).toBeTruthy();
  });
  it('me', () => {
    const service: TeacherService = TestBed.get(TeacherService);
    let result;
    service.me().subscribe((teacher) => {
      result = teacher;
    });
    const httpTestingController: HttpTestingController = TestBed.get(HttpTestingController);
    const req = httpTestingController.expectOne('http://localhost:8080/Teacher/me');
    expect(req.request.method).toEqual('GET');
    const mockReturnTeacher = new Teacher(null, null, null);
    req.flush(mockReturnTeacher);
    expect(result).toBe(mockReturnTeacher);
  });
});
