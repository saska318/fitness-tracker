import { Routes } from '@angular/router';
import {authGuard} from './core/guards/auth-guard';

import { Login } from './features/auth/login/login';
import {Register} from './features/auth/register/register';
import { WorkoutForm } from './features/workout/workout-form/workout-form';
import { ReportView} from './features/report/report-view/report-view';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'register', component: Register },

  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: 'workout', component: WorkoutForm },
      { path: 'reports', component: ReportView },
      { path: '', redirectTo: 'workout', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: '' }
];
