import {Directive, ElementRef} from '@angular/core';

@Directive({
  selector: '[appLoading]'
})
export class LoadingDirective {

  constructor(private elementRef: ElementRef) {
    const htmlButtonElement = elementRef.nativeElement as HTMLButtonElement;
    htmlButtonElement.addEventListener('click', () => {
      this.buttonOnClick(htmlButtonElement);
    });
  }
  buttonOnClick(htmlButtonElement: HTMLButtonElement) {
   const svgElement = htmlButtonElement.querySelector('svg') as SVGElement;
   svgElement.style.display = 'none';
   const loadingElement = document.createElement('i') as HTMLElement;
   loadingElement.classList.add('fa');
   loadingElement.classList.add('fa-cog');
   loadingElement.classList.add('fa-spin');
   htmlButtonElement.insertBefore(loadingElement, svgElement);
  }

}
