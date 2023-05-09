import {Component, OnInit} from '@angular/core';
import {Student} from '../../norm/entity/student';
import {Klass} from '../../norm/entity/Klass';
import {FormControl} from '@angular/forms';
import {StudentService} from '../../service/student.service';
import {Confirm, Report} from 'notiflix';
import {Page} from '../../norm/page';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.sass']
})
export class IndexComponent implements OnInit {
  /* 分页数据 */
  pages: Array<number>;
  pageData = {} as Page<Student>;
  /* 是否全部选中 */
  isCheckedAll = false;

  /*缓存要删除的学生*/
  cacheDeleteStudent: Student;

  /* 查询参数 */
  params = {
    page: 0,
    size: 2,
    klass: new Klass(null, null, null),
    name: new FormControl(),
    sno: new FormControl()
  };

  /* 分页数据 */
  pageStudent = {
    totalPages: 0,
    content: new Array<Student>()
  };
  showPopWindow = false;

  constructor(private studentService: StudentService) {
    console.log(studentService);
  }

  /**
   * 删除学生
   * @param student 学生
   */
  onDelete(student: Student): void {
    this.cacheDeleteStudent = student;
    this.showPopWindow = true;
  }

  /**
   * 删除缓存的学生后，隐藏弹窗
   */
  deleteCacheStudent() {
    const student = this.cacheDeleteStudent;
    console.log('学生的值' + student.name);
    this.studentService.deleteById(student.id).subscribe(
      () => {
        console.log('删除成功');
        this.pageStudent.content.forEach( (value, key) => {
          if (value === student ) {
            this.pageStudent.content.splice(key, 1);
          }
        });
      }, error => {
        console.log('删除失败');
      }
    );
  }

  /**
   * 加载数据
   */
  loadData() {
    const queryParams = {
      page: this.params.page,
      size: this.params.size,
      klassId: this.params.klass.id,
      name: this.params.name.value,
      sno: this.params.sno.value
    };

    this.studentService.page(queryParams)
      .subscribe((response) => {
        this.pageData = response;
      });
  }
  ngOnInit() {
    this.loadData();
  }

  /**
   * 全选框被用户点击时触发
   * @param $event checkBox弹射值
   */
  onCheckAllBoxChange($event: Event) {
    const checkbox = $event.target as HTMLInputElement;
    this.isCheckedAll = checkbox.checked;
    this.pageStudent.content.forEach((student) => {
      student.isChecked = checkbox.checked;
    });
  }

  /**
   * 单选框被用户点击时
   * @param $event 弹射值
   * @param student 当前学生
   */
  onCheckBoxChange($event: Event, student: Student) {
    const checkbox = $event.target as HTMLInputElement;
    student.isChecked = checkbox.checked;
    if (checkbox.checked) {
      let checkboxAll = true;
      this.pageData.content.forEach((value) => {
        if (!value.isChecked) {
          checkboxAll = false;
        }
      });
      this.isCheckedAll = checkboxAll;
    } else {
      this.isCheckedAll = false;
    }
  }

  /**
   * 点击分页按钮
   * @param page 要请求的页码
   */
  onPage($event: number) {
    this.params.page = $event;
    this.loadData();
  }

  /* 查询 */
  onQuery() {
    this.loadData();
  }

  /* 选择班级 */
  onSelectKlass(klass: Klass) {
    this.params.klass = klass;
  }

  /**
   * 点击确认
   */
  confirm() {
    this.deleteCacheStudent();
    this.showPopWindow = false;
  }

  /**
   * 点击取消
   */
  cancel() {
    console.log('取消');
    this.showPopWindow = false;
  }
  onBatchDeleteClick(): void {
    const ids = this.pageData.content.filter(e => e.isChecked).map(e => e.id);
    if (ids.length === 0) {
      Report.warning('出错了', '请选择要删除的学生', '返回');
    } else {
      Confirm.show('请确认', '此操作不可逆', '确认',
        '取消', () => {
        console.log(ids);
        this.studentService.bathDelete(ids).subscribe(() => {
          this.params.page = 0;
          this.loadData();
        });
        });
      }
    }
}
