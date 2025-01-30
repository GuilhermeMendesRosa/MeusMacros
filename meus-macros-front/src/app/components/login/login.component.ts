import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {User} from '../../models/User';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(public authService: AuthService) {
  }

  public user: User = {
    email: '',
    password: ''
  }

  onSubmit() {
    this.authService.login(this.user).subscribe(value => {
      console.log(value)
    })
  }

}
