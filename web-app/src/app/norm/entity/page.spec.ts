import { Page } from './page';

describe('Page', () => {
  fit('should create an instance', () => {
   let page = new Page({
      number: 2,
      size: 20,
      numberOfElements: 200,
      content: []
    });
   expect(page).toBeTruthy();
   expect(page.first).toBeFalsy();
   expect(page.last).toBeFalsy();
   page = new Page({
     number: 0,
     size: 2,
     numberOfElements: 20,
     content: []
   });
   expect(page).toBeTruthy();
   page = new Page({
      number: 10,
      size: 20,
      numberOfElements: 41,
      content: []
    });
  });
});
