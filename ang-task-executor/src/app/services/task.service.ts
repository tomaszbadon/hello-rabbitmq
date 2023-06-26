import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, catchError, distinct, flatMap, from, mergeMap, of, raceWith } from 'rxjs';
import { Task } from '../Task';
import { Tasks } from '../Tasks';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient) { }

  getTasks(): Observable<Tasks> {
    return this.http.get<Tasks>(environment.urlPrefix + '/tasks')
                    .pipe(
                      catchError(this.handleError<Tasks>('getTasks', {tasks: []}))
                    );
  }

  submitTask(): Observable<Task> {
    return this.http.post<Task>(environment.urlPrefix + '/tasks', null).pipe(
      catchError(this.handleError<Task>('getTasks', undefined))
    );
  }

  deleteTasks(tasks: Task[]): Observable<void> {
    return from(tasks).pipe(
      mergeMap(task => {
        return this.http.delete<void>(environment.urlPrefix + '/tasks/' + task.id)
      }),
      distinct(),
      catchError(this.handleError<void>('deleteTasks'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
  
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
  
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }


}
