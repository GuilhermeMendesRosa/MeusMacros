import {Component, HostListener, OnInit} from '@angular/core';
import {CalculationService} from '../../services/calculation.service';
import {Meal} from '../../models/Meal';
import {NgForOf, NgIf, NgStyle} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Goal} from '../../models/Goal';
import {GoalService} from '../../services/goal.service';

@Component({
  selector: 'app-my-meals',
  templateUrl: './my-meals.component.html',
  imports: [
    NgIf,
    NgForOf,
    FormsModule,
    NgStyle
  ],
  styleUrls: ['./my-meals.component.css']
})
export class MyMealsComponent implements OnInit {

  public selectedDate: string;

  public mealsFromAPI: Meal[] = [];

  public consumedCalories: number = 0;
  public consumedProtein: number = 0;
  public consumedCarbohydrates: number = 0;
  public consumedFat: number = 0;

  public goal: Goal = {
    calories: 2000,
    proteinPercentage: 25,
    carbohydratesPercentage: 50,
    fatPercentage: 25
  };

  public activeDeleteMeal: number | null = null;
  private pressTimer: any;

  constructor(
    private calculationService: CalculationService,
    private goalService: GoalService
  ) {
    this.selectedDate = this.getCurrentBrasiliaDateString();
  }

  ngOnInit(): void {
    this.goalService.getLatestGoal().subscribe(goal => {
      this.goal = goal;
    });
    this.fetchMeals();
  }

  private getCurrentBrasiliaDateString(): string {
    const now = new Date();
    const brasiliaOffsetMinutes = -3 * 60; // -180 minutos para UTC-3
    const localOffsetMinutes = now.getTimezoneOffset();
    const diffMinutes = brasiliaOffsetMinutes - localOffsetMinutes;
    const brasiliaTime = new Date(now.getTime() + diffMinutes * 60000);
    return brasiliaTime.toISOString().split('T')[0];
  }

  private addDays(dateStr: string, days: number): string {
    const [year, month, day] = dateStr.split('-').map(Number);
    const date = new Date(year, month - 1, day);
    date.setDate(date.getDate() + days);
    const y = date.getFullYear();
    const m = date.getMonth() + 1;
    const d = date.getDate();
    return `${y}-${m < 10 ? '0' + m : m}-${d < 10 ? '0' + d : d}`;
  }

  fetchMeals(): void {
    this.calculationService.listMeals(this.selectedDate).subscribe(
      meals => {
        this.consumedCalories = 0;
        this.consumedProtein = 0;
        this.consumedCarbohydrates = 0;
        this.consumedFat = 0;
        this.mealsFromAPI = meals;
        meals.forEach((meal: Meal) => {
          this.consumedCalories += meal.calories;
          this.consumedProtein += meal.protein;
          this.consumedCarbohydrates += meal.carbohydrates;
          this.consumedFat += meal.fat;
        });
      },
      error => {
        console.error('Erro ao buscar as refeições:', error);
      }
    );
  }

  onDateChange(newDate: string): void {
    this.selectedDate = newDate;
    this.fetchMeals();
  }

  goPreviousDay(): void {
    this.selectedDate = this.addDays(this.selectedDate, -1);
    this.fetchMeals();
  }

  goNextDay(): void {
    this.selectedDate = this.addDays(this.selectedDate, 1);
    this.fetchMeals();
  }

  get protein(): number {
    return Math.round(this.goal.calories * this.goal.proteinPercentage * 0.01 / 4);
  }

  get carbohydrates(): number {
    return Math.round(this.goal.calories * this.goal.carbohydratesPercentage * 0.01 / 4);
  }

  get fat(): number {
    return Math.round(this.goal.calories * this.goal.fatPercentage * 0.01 / 9);
  }

  getProteinProgress(): number {
    const progress = (this.consumedProtein / this.protein) * 100;
    return progress > 100 ? 100 : progress;
  }

  getCarbohydratesProgress(): number {
    const progress = (this.consumedCarbohydrates / this.carbohydrates) * 100;
    return progress > 100 ? 100 : progress;
  }

  getFatProgress(): number {
    const progress = (this.consumedFat / this.fat) * 100;
    return progress > 100 ? 100 : progress;
  }

  getCaloriesProgress(): number {
    const progress = (this.consumedCalories / this.goal.calories) * 100;
    return progress > 100 ? 100 : progress;
  }

  onMealPressStart(mealId: number): void {
    this.pressTimer = setTimeout(() => {
      this.activeDeleteMeal = mealId;
    }, 1000);
  }

  onMealPressEnd(): void {
    if (this.pressTimer) {
      clearTimeout(this.pressTimer);
      this.pressTimer = null;
    }
  }

  confirmDeleteMeal(mealId: number): void {
    this.calculationService.deleteMeal(mealId).subscribe(() => {
      this.activeDeleteMeal = null;
      this.fetchMeals();
    }, error => {
      console.error('Erro ao deletar a refeição:', error);
    });
  }

  cancelDeleteMeal(): void {
    this.activeDeleteMeal = null;
  }

  @HostListener('document:click')
  onDocumentClick(): void {
    this.activeDeleteMeal = null;
  }

}
