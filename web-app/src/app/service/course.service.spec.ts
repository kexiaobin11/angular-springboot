import { TestBed } from '@angular/core/testing';

import { CourseService } from './course.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {Course} from '../norm/entity/course';

describe('CourseService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule
    ]
  }));

  it('should be created', () => {
    const service: CourseService = TestBed.get(CourseService);
    expect(service).toBeTruthy();
  });
  it('save', () => {
    const service: CourseService = TestBed.get(CourseService);
    const testController = TestBed.get(HttpTestingController) as HttpTestingController;
    const course = new Course();
    let result: Course;
    service.save(course).subscribe( (data) => {
     result = data;
    });
    const request = testController.expectOne('http://localhost:8080/Course');
    expect(request.request.method).toEqual('POST');
    expect(request.request.body).toEqual(course);
  });
});
