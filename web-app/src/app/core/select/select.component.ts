import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.sass']
})
export class SelectComponent implements OnInit {

  /*所有对象*/
  objects: Array<Select>;

  objectSelect: FormControl;

  @Output() selected = new EventEmitter<Select>();
  @Input() set object(object: { id: number }) {this.objectSelect = new FormControl(object);
  }
  @Input() url: string;

  constructor(private httpClient: HttpClient) {
  }


  /**
   * 获取所有的对象，并传给V层
   */
  ngOnInit() {
    console.log(this.object);
    this.objectSelect = new FormControl(this.object);
    console.log('类型' + this.objectSelect);
    this.httpClient.get(this.url)
      .subscribe((objects: Array<Select>) => {
        this.objects = objects;
      });
  }

  /**
   * 比较函数，标识用哪个字段来比较两个对象是否为同一个对象
   * t2进行遍历找到，直达遍历到相同对象的值
   * @param t1 源
   * @param t2 目标
   */
  compareFn(t1: { id: number }, t2: { id: number }) {
    console.log('t1' + t1 + 't2' + t2);
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }

  onChange() {
    this.selected.emit(this.objectSelect.value);
    console.log('选中' + this.objectSelect.value);
  }

}

/**
 * 选择
 */
export class Select {
  id: number;
  name: string;

  constructor(id: number, name: string) {
    this.id = id;
    this.name = name;
  }
}
