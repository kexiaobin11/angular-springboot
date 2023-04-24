import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {By} from '@angular/platform-browser';
import {FormTest} from '../testing/FormTest';
import {TestModule} from '../test/test.module';
import {StudentService} from '../service/student.service';
import {of} from 'rxjs';
import {TeacherService} from '../service/teacher.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [
        ReactiveFormsModule,
        FormsModule,
        TestModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    component.formGroup.get('username').setValue('zhangsan');
    component.formGroup.get('password').setValue('12345678');
    fixture.detectChanges();
    const usernameValue = fixture.debugElement.query(By.css('input[name="username"]')).nativeElement;
    const passwordValue = fixture.debugElement.query(By.css('input[name="password"]')).nativeElement;
    expect(usernameValue.value).toEqual('zhangsan');
    expect(passwordValue.value).toEqual('12345678');
  });
  it('点击提交按钮', () => {
    spyOn(component, 'onSubmit');
    const button = fixture.debugElement.query(By.css('button')).nativeElement;
    button.click();
    expect(component.onSubmit).toHaveBeenCalled();
  });
  /*当 login() 方法第一次被调用时，它会返回一个值为 true 的 Observable 对象，第二次调用返回 false。*/
  fit('onSubmit', () => {
    const teacherService = TestBed.get(TeacherService) as TeacherService;
    spyOn(teacherService, 'login').and.returnValues(of(true), of(false));
    spyOn(teacherService, 'setIsLogin');
    spyOn(console, 'log');
    component.formGroup.get('username').setValue('testUsername');
    component.formGroup.get('password').setValue('testPassword');
    component.onSubmit();
    expect(teacherService.login).toHaveBeenCalledWith('testUsername', 'testPassword');
    expect(teacherService.setIsLogin).toHaveBeenCalledWith(true);
    component.onSubmit();
    expect(console.log).toHaveBeenCalledWith('用户名密码错误');
  });
});
