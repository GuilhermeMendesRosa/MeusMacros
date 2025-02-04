import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {GoalService} from '../../services/goal.service';
import {Goal} from '../../models/Goal';
import {AuthService} from '../../services/auth.service';
import {User} from '../../models/User';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  constructor(
    private goalService: GoalService,
    private authService: AuthService) {
  }

  public goal: Goal = {
    calories: 2000,
    proteinPercentage: 25,
    carbohydratesPercentage: 50,
    fatPercentage: 25
  }

  public user: User = {
    firstName: '',
    lastName: '',
  }

  ngOnInit() {
    this.authService.me().subscribe(me => {
      this.user = me;
    })
    this.goalService.getLatestGoal().subscribe(goal => {
      this.goal = goal;
    })
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
    return this.goal.proteinPercentage + this.goal.carbohydratesPercentage + this.goal.fatPercentage != 100;
  }

}
