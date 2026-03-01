import { Component, ElementRef, ViewChild, ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepicker, MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DATE_FORMATS, MAT_DATE_LOCALE, DateAdapter } from '@angular/material/core';
import { MatMomentDateModule } from '@angular/material-moment-adapter';

import { WeeklyReport } from '../../../core/models/weekly-report.model';
import { Report } from '../../../core/services/report';

import { Chart, registerables } from 'chart.js';
import { Moment } from 'moment';

Chart.register(...registerables);

export const MONTH_YEAR_FORMATS = {
  parse: {
    dateInput: 'MM/YYYY',
  },
  display: {
    dateInput: 'MMMM YYYY',
    monthYearLabel: 'MMMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-report-view',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatInputModule,
    MatButtonModule,
    MatMomentDateModule
  ],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MONTH_YEAR_FORMATS },
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ],
  templateUrl: './report-view.html',
  styleUrls: ['./report-view.css'],
})
export class ReportView {

  @ViewChild('workoutChart') workoutChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('intensityChart') intensityChartRef!: ElementRef<HTMLCanvasElement>;

  private workoutChart?: Chart;
  private intensityChart?: Chart;

  monthControl = new FormControl<Moment | null>(null);

  report: WeeklyReport[] = [];
  loading = false;
  hasSelectedMonth = false;

  constructor(
    private service: Report,
    private dateAdapter: DateAdapter<Moment>,
    private cdr: ChangeDetectorRef
  ) {
    this.dateAdapter.setLocale('en-GB');
  }

  loadSelectedMonth() {
    const date = this.monthControl.value;
    if (!date) return;

    this.hasSelectedMonth = true;
    this.fetchReport(date);
  }

  fetchReport(date: Moment) {
    const year = date.year();
    const month = date.month() + 1;

    this.loading = true;

    this.workoutChart?.destroy();
    this.intensityChart?.destroy();

    this.workoutChart = undefined;
    this.intensityChart = undefined;

    this.report = [];
    this.cdr.detectChanges();

    this.service.getMonthly(year, month).subscribe(res => {
      this.report = res;
      this.loading = false;

      this.cdr.detectChanges();

      if (!this.report.length) return;

      requestAnimationFrame(() => {
        this.buildCharts();
      });
    });
  }

  onMonthSelected(normalizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
    const selected = normalizedMonth.clone().startOf('month');

    this.monthControl.setValue(selected);
    this.hasSelectedMonth = false;

    datepicker.close();
  }

  buildCharts() {
    if (!this.workoutChartRef || !this.intensityChartRef) return;

    const labels = this.report.map(w => `Week ${w.weekNumber}`);
    const workoutCounts = this.report.map(w => w.totalWorkouts);
    const intensities = this.report.map(w => w.avgIntensity);
    const feelings = this.report.map(w => w.avgFeeling);

    this.workoutChart?.destroy();
    this.intensityChart?.destroy();

    this.workoutChart = new Chart(this.workoutChartRef.nativeElement, {
      type: 'bar',
      data: {
        labels,
        datasets: [{
          label: 'Number of Workouts',
          data: workoutCounts,
          backgroundColor: '#33b2e5'
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    });

    this.intensityChart = new Chart(this.intensityChartRef.nativeElement, {
      type: 'line',
      data: {
        labels,
        datasets: [
          {
            label: 'Avg Intensity',
            data: intensities,
            borderColor: '#33b2e5',
            backgroundColor: '#33b2e5',
            tension: 0.3
          },
          {
            label: 'Avg Fatigue',
            data: feelings,
            borderColor: '#ff8f21',
            backgroundColor: '#ff8f21',
            tension: 0.3
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    });
  }
}
