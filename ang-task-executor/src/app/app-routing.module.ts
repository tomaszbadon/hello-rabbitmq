import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TaskSchedulerComponent } from './task-scheduler/task-scheduler.component';
import { ChatComponent } from './chat/chat.component';

const routes: Routes =  [
            { path: 'task-scheduler', component: TaskSchedulerComponent },
            { path: 'chat', component: ChatComponent },
            { path: '', component: TaskSchedulerComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
