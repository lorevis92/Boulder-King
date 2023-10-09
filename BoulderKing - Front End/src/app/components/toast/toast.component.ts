// toast.component.ts
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-toast',
  template: `
    <div class="toast" [ngClass]="{show: show}">
      <div class="toast-header">
        <strong class="me-auto">{{title}}</strong>
        <button type="button" class="btn-close" (click)="close()"></button>
      </div>
      <div class="toast-body">
        {{message}}
      </div>
    </div>
  `,
  styles: [`
    .toast {
      position: fixed;
      top: 40%;
      left: 50%;
      transform: translate(-50%, -50%);
      opacity: 0;
      transition: opacity 0.3s ease;
      background-color: #E8F0FE;
      color: #212529; 
    }
    .toast.show {
      opacity: 1;
    }
    .toast-header {
    background-color: #62232F;  
    color: white;
  }
  `]
})
export class ToastComponent {
  @Input() title = '';
  @Input() message = '';
  show = false;

  close() {
    this.show = false;
  }

  open() {
  this.show = true;
  }
}


