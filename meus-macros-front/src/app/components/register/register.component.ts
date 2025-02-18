import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/User';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  imports: [
    FormsModule,
    NgIf
  ],
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  public user: User = {
    firstName: '',
    lastName: '',
    email: '',
    password: ''
  };

  public confirmPassword: string = '';

  public errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.errorMessage = '';

    if (this.user.password !== this.confirmPassword) {
      this.errorMessage = 'As senhas informadas não coincidem. Por favor, verifique e tente novamente.';
      return;
    }

    this.authService.register(this.user).subscribe({
      next: (response) => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMessage = 'Ocorreu um erro ao criar sua conta. Por favor, tente novamente mais tarde.';
        console.error('Erro no cadastro:', err);
      }
    });
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
