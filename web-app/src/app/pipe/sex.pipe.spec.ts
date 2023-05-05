import { SexPipe } from './sex.pipe';

describe('SexPipe', () => {
  fit('create an instance', () => {
    const pipe = new SexPipe();
    expect(pipe).toBeTruthy();
    expect(pipe.transform(true)).toEqual('男');
    expect(pipe.transform(false)).toEqual('女');
  });
});
