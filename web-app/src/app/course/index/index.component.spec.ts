import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexComponent } from './index.component';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TestModule} from '../../test/test.module';
import {CoreTestingModule} from '../../core/core-testing/core-testing.module';
import {RouterTestingModule} from '@angular/router/testing';
import {Student} from '../../norm/entity/student';
import {Course} from '../../norm/entity/course';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

describe('IndexComponent', () => {
  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndexComponent ],
      imports: [
        ReactiveFormsModule,
        FormsModule,
        TestModule,
        CoreTestingModule,
        RouterTestingModule,
        HttpClientTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  fit('onDelete -> 确认删除', () => {
    component.showPopWindow = false;
    const course = new Course();
    component.onDelete(course);
    fixture.detectChanges();
    expect(component.cacheCourse).toBeTruthy(course);
    expect(component.showPopWindow).toBeTruthy();
  });
});
