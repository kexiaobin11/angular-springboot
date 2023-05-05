import { Component, OnInit } from '@angular/core';
import {Course} from '../../norm/entity/course';
import {Page} from '../../norm/page';
import {FormControl} from '@angular/forms';
import {Klass} from '../../norm/entity/Klass';
import {Teacher} from '../../norm/entity/Teacher';
import {CourseService} from '../../service/course.service';
import {Student} from '../../norm/entity/student';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.sass']
})
export class IndexComponent implements OnInit {
  pages: Array<number>;
  params = {
    page: 0,
    size: 2,
    name: new FormControl(),
    klass: new Klass(null, null, null),
    teacher: new Teacher(null, null, null)
  };
  coursePage: { totalPages: number; content: Array<Course> };

  constructor(private courseService: CourseService) { }

  ngOnInit() {
    this.loadData();
  }

  onQuery(): void {}
  onSelectKlass($event: Klass): void {
    this.params.klass = $event;
  }
  onSelectTeacher($event: Teacher): void {
    this.params.teacher = $event;
  }
  onDelete(course: Course): void {
  }

  makePagesByTotalPages(currentPage: number, totalPages: number): Array<number> {
    if (totalPages > 0) {
      if (totalPages <= 5) {
        return this.makePages(0, totalPages - 1);
      }
      if (currentPage < 2) {
        return this.makePages(0, 4);
      }
      if (currentPage > totalPages - 3) {
        return this.makePages(totalPages - 5, totalPages - 1);
      }
      /* 总页数大于5，且为中间页码*/
      return this.makePages(currentPage - 2, currentPage + 2);
    }
    return new Array();
  }
  makePages(begin: number, end: number): Array<number> {
    const result = new Array<number>();
    for (; begin <= end; begin++) {
      result.push(begin);
    }
    return result;
  }
  loadData() {
    const queryParams = {
      page: this.params.page,
      size: this.params.size,
      name: this.params.name.value,
      klassId: this.params.klass.id,
      teacherId: this.params.teacher.id
    };
    this.courseService.page(queryParams).subscribe( (respone) => {
      this.coursePage = respone;
      this.pages = this.makePagesByTotalPages(this.params.page, respone.totalPages);
    }, error => {
      console.log('请求失败');
    });
  }

}
