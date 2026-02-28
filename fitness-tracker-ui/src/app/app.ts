import { Component, inject } from '@angular/core';
import {Router, RouterOutlet, NavigationEnd} from '@angular/router';
import {CommonModule} from '@angular/common';
import {Toolbar} from './shared/components/toolbar/toolbar';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
    CommonModule,
    Toolbar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  private router = inject(Router);

  showToolbar = false;

  constructor() {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        const url = this.router.url;

        this.showToolbar = !(url.includes('login') || url.includes('register'));
      });
  }
}
