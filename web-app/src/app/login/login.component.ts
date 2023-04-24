import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {TeacherService} from '../service/teacher.service';
import {AppComponent} from '../app.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  formGroup: FormGroup;
  constructor(private teacherService: TeacherService) { }

  ngOnInit() {
    this.formGroup = new FormGroup({
      password: new FormControl(''),
      username: new FormControl('')
    });
  }

  /**
   * 请求后端返回，请求成功返回true，把isLogin设置为true
   */
  onSubmit(): void {
    const username = this.formGroup.get('username').value;
    const password = this.formGroup.get('password').value;
    this.teacherService.login(username, password).subscribe( (result) => {
      if (result) {
        this.teacherService.setIsLogin(true);
      } else {
        console.log('用户名密码错误');
      }
    });
  }
}
