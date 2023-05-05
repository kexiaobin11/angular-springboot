import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MultipleSelectComponent } from './multiple-select/multiple-select.component';
import {CoreTestingController} from './core-testing-controller';
import { KlassSelectComponent } from './klass-select/klass-select.component';

@NgModule({
  declarations: [MultipleSelectComponent, KlassSelectComponent],
  imports: [
    CommonModule
  ],
  exports: [
    MultipleSelectComponent,
    KlassSelectComponent
  ],
  providers: [
    CoreTestingController,
  ]
})
export class CoreTestingModule { }
