import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import {LoginRequest} from '../models/login-request.model';

@Injectable({ providedIn: 'root' })
export class Auth {

  private base = 'http://localhost:8090/api/auth';
  private TOKEN_KEY = 'token';

  constructor(private http: HttpClient) {}

  login(data: LoginRequest) {
    return this.http.post<{ token: string }>(this.base + '/login', data);
  }

  register(data: { email: string; password: string }) {
    return this.http.post<any>(this.base + '/register', data).pipe(
      tap(res => this.saveToken(res.token))
    );
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
