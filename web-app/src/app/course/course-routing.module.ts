import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AddComponent} from './add/add.component';
import {IndexComponent} from './index/index.component';


const routes: Routes = [
  {
    path: 'add',
    component: AddComponent
  },
  {
    path: '',
    component: IndexComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CourseRoutingModule { }
