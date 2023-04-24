import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {NavComponent} from './nav.component';
import {RouterTestingModule} from '@angular/router/testing';;
import {FormTest} from '../testing/FormTest';
import {TeacherService} from '../service/teacher.service';
import {TestModule} from '../test/test.module';

describe('NavComponent', () => {
  let component: NavComponent;
  let fixture: ComponentFixture<NavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NavComponent],
      imports: [RouterTestingModule, TestModule],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('click()', () => {
    expect(component).toBeTruthy();
    spyOn(component, 'onLogout');
    FormTest.clickButton(fixture, 'button');
    expect(component.onLogout).toHaveBeenCalled();
  });
  it('测试依赖注入', () => {
    const service = TestBed.get(TeacherService);
    console.log(service);
  });
  fit('onLogout', () => {
    const service = TestBed.get(TeacherService) as TeacherService;
    spyOn(service, 'setIsLogin');
    component.onLogout();
    expect(service.setIsLogin).toHaveBeenCalledWith(false);
    console.log(service);
  });
});
