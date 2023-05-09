import { Injectable } from '@angular/core';
import {Course} from '../norm/entity/course';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CourseStubService {

  constructor() { }
  save(course: Course): Observable<Course> {
    return null;
  }

  existsByName(name: string): Observable<boolean> {
   return of(true);
  }
  page(params: {name?: string, klassId?: number, teacherId?: number, page?: number, size?: number}):
    Observable<{totalPages: number, content: Array<Course>}> {
    return null;
  }
}
