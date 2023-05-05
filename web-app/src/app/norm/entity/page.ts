export class Page<T> {
  content: T[];
  last: boolean;
  number: number;
  size: number;
  numberOfElement: number;
  first: boolean;
  constructor(data: {
    content: T[],
    last?: boolean,
    number: number,
    size: number,
    numberOfElements: number,
    first?: boolean
  }) {
    this.content = data.content;
    this.number = data.number;
    this.size = data.size;
    if (data.last !== undefined) {
      this.last = data.last;
    } else {
      this.last = (this.number + 1) * this.size >= this.numberOfElement ? true : false;
    }
    if (data.first !== undefined) {
      this.first = data.first;
    } else {
      this.first = this.number === 0 ? true : false;
    }
  }

}
