import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WeeklyReport} from '../models/weekly-report.model';

@Injectable({
  providedIn: 'root',
})
export class Report {
  private base = 'http://localhost:8090/api/reports';

  constructor(private http: HttpClient) {}

  getMonthly(year: number, month: number) {
    return this.http.get<WeeklyReport[]>(`${this.base}?year=${year}&month=${month}`);
  }
}
