import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Page} from '../../norm/page';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.sass']
})
export class PageComponent implements OnInit {
  inputPage: Page<any> = new Page({
    content: [],
    number: 0,
    size: 0,
    numberOfElements: 0
  });
  pages: number[] = [];
  currentPage = 0;

  @Input()
  set page(page: Page<any>) {
    this.inputPage = page;
    let maxCount;
    let begin;
    if (page.totalPages > 7) {
      maxCount = 7;
      begin = this.inputPage.number - 3;
      if (begin < 0) {
        begin = 0;
      } else if (begin > this.inputPage.totalPages - 7) {
        begin = this.inputPage.totalPages - 7;
      }
    } else {
      maxCount = this.inputPage.totalPages;
      begin = 0;
    }
    // 生成页数数组
    this.pages = [];
    // @ts-ignore
    for (let i = 0; i < maxCount; begin++, i++) {
      // @ts-ignore
      this.pages.push(begin);
    }
    // 设置当前页
    this.currentPage = this.inputPage.number;
  }

  @Output()
  bePageChange = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit(): void {
    console.log('page组件调用ngOnInit()方法');
    console.log(this.inputPage);
    console.log('当前页', this.inputPage.number);
    console.log('总页数', this.inputPage.totalPages);
  }

  onPage(page: number): void {
    this.currentPage = page;
    // 点击页码时弹出该页码
    this.bePageChange.emit(this.currentPage);
  }
}
