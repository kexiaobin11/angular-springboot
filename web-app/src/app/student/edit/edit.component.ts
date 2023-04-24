import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Student} from '../../norm/entity/student';
import {Klass} from '../../norm/entity/Klass';
import {ActivatedRoute} from '@angular/router';
import {StudentService} from '../../service/student.service';
import {validate} from 'codelyzer/walkerFactory/walkerFn';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.sass']
})
export class EditComponent implements OnInit {
  /* 使用ViewChild在C层中使用V层中定义的 跳转到首页按钮 */
  // @ViewChild('linkToIndex', {static: true})
  // linkToIndex: ElementRef;
  @ViewChild('linkToIndex', {static: true})
  linkToIndex: ElementRef;

  formGroup: FormGroup;
  student: Student = new Student();

  constructor(private activatedRoute: ActivatedRoute,
              private studentService: StudentService) {
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe((param: { id: number }) => {
      this.student.id = param.id;
      this.loadStudentById(this.student.id);
    });

    this.formGroup = new FormGroup({
      name: new FormControl('', [Validators.minLength(2), Validators.maxLength(10)]),
      sno: new FormControl('', [Validators.minLength(6), Validators.maxLength(6)])
    });
  }

  onSubmit() {
    this.student.name = this.formGroup.get('name').value;
    this.student.sno = this.formGroup.get('sno').value;
    this.student.klass = this.student.klass;
    this.update(this.student);
  }
  update(student: Student) {
    this.studentService.update(student.id, student)
      .subscribe((result) => {
        this.student = result;
        this.linkToIndex.nativeElement.click();
      });
  }
  loadStudentById(id: number) {
    this.studentService.getById(id)
      .subscribe(student => {
        this.student = student;
        this.setFormGroupValue(this.student);
      });
  }
  setFormGroupValue(student: Student) {
    this.formGroup.setValue({
      name: student.name,
      sno: student.sno
    });
  }
  onSelectKlass($event: Klass) {
    this.student.klass = $event;
  }
  disableSubmitButton(formGroup: FormGroup) {
    return formGroup.get('name').errors !== null || formGroup.get('sno').errors !== null;
  }
}
