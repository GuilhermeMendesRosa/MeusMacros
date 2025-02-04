import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {User} from '../../models/User';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(
    public authService: AuthService,
    private router: Router
  ) {
  }

  public user: User = {
    email: '',
    password: ''
  }

  onSubmit() {
    this.authService.login(this.user).subscribe(value => {
      this.router.navigate(["/"]);
    })
  }

  // Método para redirecionar para a tela de login
  goToRegister(): void {
    this.router.navigate(['/register']);
  }

}
