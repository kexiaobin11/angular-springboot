import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TeacherService} from './service/teacher.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {
  isLogin = false;
  constructor(private route: Router,
              private teacherService: TeacherService) {
  }
 /* 订阅请求得到isLogin的值*/
  ngOnInit(): void {
    this.teacherService.isLogin$.subscribe(isLogin => this.isLogin = isLogin);
  }
}
