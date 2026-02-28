import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators, AbstractControl, ReactiveFormsModule} from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import {Auth} from '../../../core/services/auth';
import {CommonModule} from '@angular/common';
import {MatSnackBarModule} from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatSnackBarModule
  ],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class Register implements OnInit{

  registerForm!: any;
  showPassword = false;
  showConfirm = false;

  constructor(
    private fb: FormBuilder,
    private auth: Auth,
    private router: Router,
    private snack: MatSnackBar
  ) {}

  ngOnInit() {
    this.registerForm= this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}$/)
      ]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }

  passwordMatchValidator(group: AbstractControl) {
    const pass = group.get('password')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return pass === confirm ? null : { mismatch: true };
  }

  onSubmit() {
    if (this.registerForm.invalid) return;

    const { email, password } = this.registerForm.value;

    this.auth.register({ email, password }).subscribe({
      next: () => {
        this.snack.open('Account created successfully!', 'Close', {
          duration: 3000,
          verticalPosition: 'top',
          horizontalPosition: 'center',
          panelClass: 'success-snackbar'
        });
        this.router.navigate(['/login']);
      },
      error: err => {
        if (err.status === 409) {
          this.snack.open('User already exists', 'Close', {
            duration: 3000,
            verticalPosition: 'bottom',
            horizontalPosition: 'center',
            panelClass: 'error-snackbar'
          });
        } else {
          this.snack.open('Registration failed', 'Close', {
            duration: 3000,
            verticalPosition: 'bottom',
            horizontalPosition: 'center',
            panelClass: 'error-snackbar'
          });
        }
      }
    });
  }

  togglePassword() { this.showPassword = !this.showPassword; }
  toggleConfirm() { this.showConfirm = !this.showConfirm; }
}
