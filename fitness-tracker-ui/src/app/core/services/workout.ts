import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WorkoutModel} from '../models/workout.model';

@Injectable({
  providedIn: 'root',
})
export class Workout {
  private base = 'http://localhost:8090/api/workouts';

  constructor(private http: HttpClient) {}

  save(workout: WorkoutModel) {
    return this.http.post(this.base, workout);
  }
}
