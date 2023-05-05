import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Klass} from '../../../norm/entity/Klass';
import {CoreTestingController} from '../core-testing-controller';

@Component({
  selector: 'app-klass-select',
  template: `
    <p>
      klass-select works!
    </p>
  `,
  styles: []
})
export class KlassSelectComponent implements OnInit {
  @Output() selected = new EventEmitter<Klass>();
  @Input() klass: Klass;
  constructor(private controller: CoreTestingController) {
    this.controller.addUnit(this);
  }

  ngOnInit() {
  }

}
