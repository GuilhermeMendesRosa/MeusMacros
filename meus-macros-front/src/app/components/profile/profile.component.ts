import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {GoalService} from '../../services/goal.service';
import {AuthService} from '../../services/auth.service';
import {Goal} from '../../models/Goal';
import {User} from '../../models/User';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  imports: [FormsModule, NgIf],
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  // Propriedade que indica se a seção "Ajustar Metas" está aberta
  public isAdjustMetasOpen: boolean = false;

  public goal: Goal = {
    calories: 2000,
    proteinPercentage: 25,
    carbohydratesPercentage: 50,
    fatPercentage: 25
  };

  public user: User = {
    firstName: '',
    lastName: '',
  };

  constructor(
    private goalService: GoalService,
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.authService.me().subscribe(me => {
      this.user = me;
    });
    this.goalService.getLatestGoal().subscribe(goal => {
      this.goal = goal;
    });
  }

  get proteinGrams(): number {
    return Math.round((this.goal.proteinPercentage / 100 * this.goal.calories) / 4);
  }

  get carbsGrams(): number {
    return Math.round((this.goal.carbohydratesPercentage / 100 * this.goal.calories) / 4);
  }

  get fatsGrams(): number {
    return Math.round((this.goal.fatPercentage / 100 * this.goal.calories) / 9);
  }

  public saveGoals() {
    this.goalService.createGoal(this.goal).subscribe(goal => {
      this.goal = goal;
    });
  }

  public isDisabled(): boolean {
    return this.goal.proteinPercentage + this.goal.carbohydratesPercentage + this.goal.fatPercentage !== 100;
  }

  // Atualiza a propriedade isAdjustMetasOpen de acordo com o estado do <details>
  public onToggle(event: Event): void {
    const details = event.target as HTMLDetailsElement;
    this.isAdjustMetasOpen = details.open;
  }

  // Método de logout: limpa o token e redireciona para /login
  public logout(): void {
    localStorage.removeItem('auth_token');
    this.router.navigate(['/login']);
  }
}
