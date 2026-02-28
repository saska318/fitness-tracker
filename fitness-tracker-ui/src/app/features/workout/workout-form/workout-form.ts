import {Component, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Workout} from '../../../core/services/workout';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import { MatSliderModule } from '@angular/material/slider';

@Component({
  selector: 'app-workout-form',
  imports: [
    MatSnackBarModule,
    ReactiveFormsModule,
    CommonModule,
    MatSliderModule
  ],
  templateUrl: './workout-form.html',
  styleUrls: ['./workout-form.css']
})
export class WorkoutForm implements OnInit {
  form!: any;
  constructor(
    private fb: FormBuilder,
    private service: Workout,
    private snack: MatSnackBar
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      type: ['CARDIO', Validators.required],
      durationMinutes: [null, [Validators.required, Validators.min(1)]],
      calories: [null, [Validators.required, Validators.min(0)]],
      intensity: [5, Validators.required],
      feeling: [5, Validators.required],
      notes: [''],
      workoutDate: [null, Validators.required]
    });
  }

  submit() {
    if (this.form.invalid) return;

    this.service.save(this.form.value).subscribe(() => {

      this.snack.open('Workout successfully added.', 'Close', {
        duration: 3000,
        horizontalPosition: 'right',
        verticalPosition: 'bottom',
        panelClass: ['success-snackbar']
      });

      this.form.reset({
        type: 'CARDIO',
        intensity: 5,
        feeling: 5
      });
    });
  }
}
