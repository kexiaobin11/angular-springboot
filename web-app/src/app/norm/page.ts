export class Page<T> {
  content: T;
  totalPages: number;
  constructor(params?: { content?: T, totalPages?: number }) {
    if (params) {
      if (params.content) {
        this.content = params.content;
      }
      if (params.totalPages) {
        this.totalPages = params.totalPages;
      }
    }
  }
  }
