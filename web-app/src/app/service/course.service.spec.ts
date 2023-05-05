import { TestBed } from '@angular/core/testing';

import { CourseService } from './course.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {Course} from '../norm/entity/course';
import {Student} from '../norm/entity/student';
import {HttpRequest} from '@angular/common/http';

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
  /*
  *测试existByName 是否能使用
  * */
  it('existsByName', () => {
    const service: CourseService = TestBed.get(CourseService);
    const name = 'test';
    let result;
    service.existsByName(name).subscribe((data) => {
      result = data;
    });
    const testController = TestBed.get(HttpTestingController) as HttpTestingController;
    const request = testController.expectOne(req => req.url === 'http://localhost:8080/Course/existsByName');
    expect(request.request.method).toEqual('GET');
    expect(request.request.params.get('name')).toEqual('test');
  });
  fit( 'page ', () => {
    const service: CourseService = TestBed.get(CourseService);
    const mockResult = {
      totalPages: 10,
      content: new Array(new Course(), new Course())
    };
    let called = false;
    service.page({}).subscribe( (success: {totalPages: number, content: Array<Course>}) => {
      called = false;
      expect(success.totalPages).toEqual(10);
      expect(success.content.length).toBe(2);
    });
    const req = TestBed.get(HttpTestingController).expectOne((request: HttpRequest<any>) => {
      return request.url === 'http://localhost:8080/Course';
    });
    expect(req.request.method).toEqual('GET');
  });
});
