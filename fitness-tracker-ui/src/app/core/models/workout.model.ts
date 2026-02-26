export interface Workout {
  type: 'CARDIO' | 'STRENGTH' | 'FLEXIBILITY';
  durationMinutes: number;
  calories: number;
  intensity: number;
  feeling: number;
  notes: string;
  workoutDate: string;
}
