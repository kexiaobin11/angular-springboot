import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SelectComponent} from './select/select.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MultipleSelectComponent } from './multiple-select/multiple-select.component';
import {KlassSelectComponent} from './klass-select/klass-select.component';
import { PageComponent } from './page/page.component';


@NgModule({
  declarations: [SelectComponent, MultipleSelectComponent, KlassSelectComponent, PageComponent],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
    ],
  exports: [
    SelectComponent,
    MultipleSelectComponent,
    KlassSelectComponent,
    PageComponent
  ]
})
export class CoreModule {
}
