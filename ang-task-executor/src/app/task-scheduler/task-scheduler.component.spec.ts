import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskSchedulerComponent } from './task-scheduler.component';

describe('TaskSchedulerComponent', () => {
  let component: TaskSchedulerComponent;
  let fixture: ComponentFixture<TaskSchedulerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TaskSchedulerComponent]
    });
    fixture = TestBed.createComponent(TaskSchedulerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
