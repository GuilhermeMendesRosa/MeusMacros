import {Component} from '@angular/core';

@Component({
  selector: 'app-profile',
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user = {
    nome: 'João Silva',
    email: 'joao.silva@email.com',
    telefone: '+55 11 98765-4321',
    nascimento: '01/01/1990',
    avatar: 'https://tools-api.webcrumbs.org/image-placeholder/120/120/avatars/1'
  };

}
