import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexComponent } from './index.component';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TestModule} from '../../test/test.module';
import {CoreTestingModule} from '../../core/core-testing/core-testing.module';
import {RouterTestingModule} from '@angular/router/testing';

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
        RouterTestingModule
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
});
