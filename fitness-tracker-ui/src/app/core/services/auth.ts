import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class Auth {

  private base = 'http://localhost:8090/api/auth';
  private TOKEN_KEY = 'fitness_token';

  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post(this.base + '/login', data)
      .pipe(tap((token: any) => this.saveToken(token)));
  }

  register(data: any) {
    return this.http.post(this.base + '/register', data)
      .pipe(tap((token: any) => this.saveToken(token)));
  }

  saveToken(token: string) {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
}
