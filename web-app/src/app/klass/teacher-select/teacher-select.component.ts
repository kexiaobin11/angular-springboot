import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Teacher} from '../../norm/entity/Teacher';
import {FormControl} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {Klass} from '../../norm/entity/Klass';

@Component({
  selector: 'app-teacher-select',
  templateUrl: './teacher-select.component.html',
  styleUrls: ['./teacher-select.component.sass']
})
export class TeacherSelectComponent implements OnInit {
  /*所有教师*/
  teachers: Array<Teacher>;

  teacherSelect: FormControl;

  @Output() selected = new EventEmitter<Teacher>();
  @Input() teacher: { id: number };

  constructor(private httpClient: HttpClient) {
  }
  url = 'http://localhost:8080/Teacher';

  /**
   * 获取所有的教师，并传给V层
   */
  ngOnInit() {
  }
  onSelected(teacher: Teacher): void {
    console.log(teacher);
    this.selected.emit(teacher);
  }


}
