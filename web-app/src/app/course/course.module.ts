import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CourseRoutingModule } from './course-routing.module';
import { AddComponent } from './add/add.component';
import { KlassMultipleSelectComponent } from './klass-multiple-select/klass-multiple-select.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {KlassModule} from '../klass/klass.module';
import {CoreModule} from '../core/core.module';
import { IndexComponent } from './index/index.component';
import {KlassSelectComponent} from '../core/klass-select/klass-select.component';


@NgModule({
  declarations: [AddComponent, KlassMultipleSelectComponent, IndexComponent],
  imports: [
    CommonModule,
    CourseRoutingModule,
    ReactiveFormsModule,
    KlassModule,
    CoreModule,
    FormsModule
  ]
})
export class CourseModule { }
