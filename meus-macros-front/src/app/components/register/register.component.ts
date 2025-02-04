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
  // Dados do usuário para o cadastro
  public user: User = {
    firstName: '',
    lastName: '',
    email: '',
    password: ''
  };

  // Campo para confirmação de senha
  public confirmPassword: string = '';

  // Propriedade para armazenar a mensagem de erro
  public errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // Método chamado quando o formulário é enviado
  onSubmit(): void {
    // Limpa a mensagem de erro antes da verificação
    this.errorMessage = '';

    // Verifica se as senhas coincidem
    if (this.user.password !== this.confirmPassword) {
      this.errorMessage = 'As senhas informadas não coincidem. Por favor, verifique e tente novamente.';
      return;
    }

    // Chama o serviço de autenticação para realizar o registro
    this.authService.register(this.user).subscribe({
      next: (response) => {
        // Em caso de sucesso, navega para a tela de login
        this.router.navigate(['/login']);
      },
      error: (err) => {
        // Exibe um erro amigável em caso de falha no cadastro
        this.errorMessage = 'Ocorreu um erro ao criar sua conta. Por favor, tente novamente mais tarde.';
        console.error('Erro no cadastro:', err);
      }
    });
  }

  // Método para redirecionar para a tela de login
  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
