import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Course} from '../../norm/entity/course';
import {CourseService} from '../../service/course.service';
import {UniqueNameValidator} from '../validator/unique-name-validator';
import {Teacher} from '../../norm/entity/Teacher';
import {Klass} from '../../norm/entity/Klass';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.sass']
})
export class EditComponent implements OnInit {

  @ViewChild('linkToIndex', {static: true})
  linkToIndex: ElementRef;

  formGroup: FormGroup;
  course: Course;
  teacher: Teacher;
  klasses: Array<Klass>;

  constructor(private formBuilder: FormBuilder,
              private courseService: CourseService,
              private activatedRoute: ActivatedRoute,
              private uniqueNameValidator: UniqueNameValidator) {
    this.formGroup = this.formBuilder.group({
      name: ['', [Validators.minLength(2), Validators.required],
        this.uniqueNameValidator.validate]
    });
    this.course = new Course();
  }

  ngOnInit() {
   this.activatedRoute.params.subscribe( (params: {id: number}) => {
     this.course.id = params.id;
     this.loadCourseById(this.course.id);
   });
  }

  onTeacherSelect($event: Teacher) {
    this.course.teacher = $event;
  }

  onSubmit() {
    this.course.name = this.formGroup.get('name').value;
    console.log(this.course);
    this.courseService.update(this.course.id, this.course).subscribe(() => {
      console.log('修改成功');
      this.goToIndex();
    }, error => {
      console.log('修改失败');
    });
  }
  goToIndex(): void {
    this.linkToIndex.nativeElement.click();
  }
  /*传入多个id*/
  onKlassesChange($event: Klass[]) {
    this.course.klasses = $event;
  }
  loadCourseById(id: number): void {
    this.courseService.getById(id).subscribe( (course: Course) => {
     this.course = course;
     this.klasses = course.klasses;
     this.teacher = course.teacher;
     this.setFormGroupValue(this.course);
     console.log('获取成功');
    }, () => {
      console.log('获取失败');
    });
  }
  setFormGroupValue(course: Course) {
    this.formGroup.setValue({
      name: course.name,
    });
  }
}
