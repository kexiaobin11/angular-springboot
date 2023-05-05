import { AuthTokenInterceptor } from './auth-token-interceptor';
import {async, TestBed} from '@angular/core/testing';
import {TeacherService} from '../service/teacher.service';
import {TeacherStubService} from '../service/teacher-stub.service';

describe('AuthTokenInterceptor', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: TeacherService, useClass: TeacherStubService}
      ]
    })
      .compileComponents();
  }));
  it('should create an instance', () => {
    const teacherService = TestBed.get(TeacherService);
    expect(new AuthTokenInterceptor(teacherService)).toBeTruthy();
  });
});
