import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Course} from '../norm/entity/course';
import {Observable} from 'rxjs';
import {Page} from '../norm/page';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private url = '/Course';
  constructor(private httpClient: HttpClient) {
  }

  /**
   * 保存课程
   * @param course 课程
   */
  save(course: Course): Observable<Course> {
    return this.httpClient.post<Course>(this.url, course);
  }
  /*
  * 判断课程是否存在
  * @param name 课程名称
  * */
  existsByName(name: string): Observable<boolean> {
    const url = this.url + '/existsByName';
    return this.httpClient.get<boolean>(url, {params: {name}});
  }
  /**
   * 分页
   * @param params name课程名称  klassId 班级 teacherId 教师
   */
  page(params: {name?: string, klassId?: number, teacherId?: number, page?: number, size?: number}):
    Observable<{totalPages: number, content: Array<Course>}> {
    if (params.page === undefined) {
      params.page = 0;
    }
    if (params.size === undefined) {
      params.size = 10;
    }
    const queryParams = new HttpParams()
      .set('name', params.name ? params.name : '')
      .set('klassId', params.klassId ? params.klassId.toString() : '')
      .set('teacherId', params.teacherId ? params.klassId.toString() : '')
      .set('page', params.page.toString())
      .set('size', params.size.toString());
    console.log(queryParams);
    return this.httpClient.get<{totalPages: number, content: Array<Course>}>(this.url,  {params: queryParams});
  }
  delete(id: number): Observable<boolean> {
    return null;
  }
}
