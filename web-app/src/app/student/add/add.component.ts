import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Student} from '../../norm/entity/student';
import {FormControl, FormGroup} from '@angular/forms';
import {StudentService} from '../../service/student.service';
import {Klass} from '../../norm/entity/Klass';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.sass']
})
export class AddComponent implements OnInit {
  /* 使用ViewChild在C层中使用V层中定义的 跳转到首页按钮 */
  @ViewChild('linkToIndex', {static: true})
  linkToIndex: ElementRef;

  student: Student = new Student();

  formGroup: FormGroup;

  klass: Klass;

  constructor(private studentService: StudentService) {
  }
  goToIndex(): void {
    this.linkToIndex.nativeElement.click();
  }

  ngOnInit() {
    this.formGroup = new FormGroup({
      name: new FormControl(''),
      sno: new FormControl('')
    });
  }

  onSelectKlass(klass: Klass): void {
    this.klass = klass;
  }

  onSubmit(): void {
    this.student = this.formGroup.value;
    this.student.klass = this.klass;
    this.studentService.save(this.student).subscribe((student: Student) => {
      console.log(student);
      this.goToIndex();
    });
  }

}
