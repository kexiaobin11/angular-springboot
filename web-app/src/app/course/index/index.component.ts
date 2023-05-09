import {Component, OnInit, ViewChild} from '@angular/core';
import {Course} from '../../norm/entity/course';
import {FormControl} from '@angular/forms';
import {Klass} from '../../norm/entity/Klass';
import {Teacher} from '../../norm/entity/Teacher';
import {CourseService} from '../../service/course.service';
import {Page} from '../../norm/page';
import {Confirm, Report} from 'notiflix';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.sass']
})
export class IndexComponent implements OnInit {
  showPopWindow = false;
  cacheCourse: Course;
  pageData = {} as Page<Course>;
  params = {
    page: 0,
    size: 2,
    name: new FormControl(),
    klass: new Klass(null, null, null),
    teacher: new Teacher(null, null, null)
  };
  /* 是否全部选中 */
  isCheckedAll = false;

  constructor(private courseService: CourseService) { }

  ngOnInit() {
    this.loadData();
  }

  onQuery(): void {
    this.params.page = 0;
    this.loadData();
  }
  onSelectKlass($event: Klass): void {
    this.params.klass = $event;
  }
  onSelectTeacher($event: Teacher): void {
    this.params.teacher = $event;
  }
  onDelete(course: Course): void {
    this.cacheCourse = course;
    this.showPopWindow = true;
  }
  loadData() {
    const queryParams = {
      page: this.params.page,
      size: this.params.size,
      name: this.params.name.value,
      klassId: this.params.klass.id,
      teacherId: this.params.teacher.id
    };
    this.courseService.page(queryParams)
      .subscribe((response) => {
        this.pageData = response;
      });
  }
  onPage($event: number) {
    this.params.page = $event;
    this.loadData();
  }
  cancel(): void {
    this.showPopWindow = false;
  }

  confirm(): void {
    this.deleteCacheCourse();
    this.showPopWindow = false;
  }
  onCheckBoxChange($event: Event, course: Course): void {
    const checkbox = $event.target as HTMLInputElement;
    course.isChecked = checkbox.checked;
    if (checkbox.checked) {
      let checkedAll = true;
      this.pageData.content.forEach((value, index) => {
        if (!value.isChecked) {
          checkedAll = false;
        }
      });
      this.isCheckedAll = checkedAll;
    } else {
     this.isCheckedAll = false;
    }
  }
  deleteCacheCourse(): void {
    const course = this.cacheCourse;
    this.courseService.delete(course.id).subscribe(() => {
      this.pageData.content.forEach( (value, index) => {
        if (value === course) {
          this.pageData.content.splice(index, 1);
          console.log('删除失败');
        }
      }, () => {
        console.log('删除成功');
      });
    });
  }
  onCheckAllBoxChange($event): void {
    const checkbox = $event.target as HTMLInputElement;
    this.isCheckedAll = checkbox.checked;
    this.pageData.content.forEach( (value) => {
      value.isChecked = checkbox.checked;
    });
  }
  onBatchDeleteClick(): void {
    /*
    * 监听s.isChecked是否为真，为真获取到为真的id
    * */
    const beDeleteIds = this.pageData.content.filter(s => s.isChecked).map(d => d.id);
    if (beDeleteIds.length === 0) {
      Report.warning('出错了', '请先选择要删除的学生', '返回');
    } else {
      Confirm.show('请确认', '此操作不可逆', '确认',
        '取消', () => {
          this.courseService.beDeleteIds(beDeleteIds).subscribe(() => {
            this.loadData();
          });
        });
    }
  }
}
