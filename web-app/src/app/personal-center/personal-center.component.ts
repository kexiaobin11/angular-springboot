import { Component, OnInit } from '@angular/core';
import {Teacher} from '../norm/entity/Teacher';
import {TeacherService} from '../service/teacher.service';

@Component({
  selector: 'app-personal-center',
  templateUrl: './personal-center.component.html',
  styleUrls: ['./personal-center.component.sass']
})
export class PersonalCenterComponent implements OnInit {

  teacher: Teacher;
  constructor(private teacherService: TeacherService) { }

  ngOnInit() {
    this.teacherService.me().subscribe( (teacher) => {
      this.teacher = teacher;
    });
  }

}
