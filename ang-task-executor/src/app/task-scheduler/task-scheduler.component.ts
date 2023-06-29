import { Component, OnInit } from '@angular/core';
import { Task } from '../Task';
import { TaskService } from '../services/task.service';
import { RxStomp } from '@stomp/rx-stomp';
import { filter, map } from 'rxjs';

@Component({
  selector: 'app-task-scheduler',
  templateUrl: './task-scheduler.component.html',
  styleUrls: ['./task-scheduler.component.sass']
})
export class TaskSchedulerComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private taskService: TaskService) { }

  ngOnInit() {
    this.taskService.getTasks()
      .pipe(map(t => t.tasks.sort((a, b) => b.id - a.id)))
      .subscribe(tasks => {
        this.tasks = tasks
      });

    const rxStomp = new RxStomp();

    rxStomp.configure({
      brokerURL: 'ws://localhost:15674/ws',
    });

    rxStomp.activate();

    const subscription = rxStomp.watch({ destination: "/amq/queue/active-task" })
      .subscribe((message) => {
        let task: Task = JSON.parse(message.body);
        let found = this.tasks.find(t => t.id === task.id);
        found!.progress = task.progress
      });

  }

  submitNewTask() {
    this.taskService.submitTask().subscribe(task => {
      this.tasks.unshift(task);
    });
  }

  deleteCompletedTasks() {
    let completedTasks = this.tasks.filter(t => t.progress === 100);
    this.taskService.deleteTasks(completedTasks).subscribe(() => {
      let tasks = this.tasks.filter(t => !completedTasks.includes(t));
      this.tasks = tasks;
    });
  }

}
