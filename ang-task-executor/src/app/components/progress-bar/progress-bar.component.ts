import { Component, Input } from '@angular/core';
import { Task } from 'src/app/Task';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.sass']
})
export class ProgressBarComponent {

  @Input() task: Task | null = null;

  public get width():string {
    return this.task?.progress + '%';
  }

}
