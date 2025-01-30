import {Component} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {FooterComponent} from './components/footer/footer.component';
import {NgIf} from '@angular/common';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FooterComponent, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'meus-macros-front';

  constructor(
    public router: Router) {
  }

  public get showMenu(): boolean {
    return !this.router.url.includes('/login') && !this.router.url.includes('/signup');
  }

}
