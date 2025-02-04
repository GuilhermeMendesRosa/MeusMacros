import { Component, OnInit } from '@angular/core';
import { CalculationService } from '../../services/calculation.service';
import { Meal } from '../../models/Meal';
import { NgForOf, NgIf, NgStyle } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Goal } from '../../models/Goal';
import { GoalService } from '../../services/goal.service';

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

  // Inicializa com a data de hoje no formato YYYY-MM-DD
  public selectedDate: string = new Date().toISOString().split('T')[0];

  // Array para armazenar as refeições retornadas pela API
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

  constructor(
    private calculationService: CalculationService,
    private goalService: GoalService) {
  }

  ngOnInit(): void {
    this.goalService.getLatestGoal().subscribe(goal => {
      this.goal = goal;
    });
    this.fetchMeals();
  }

  // Busca as refeições a partir da API com base na data selecionada
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

  // Atualiza a data e refaz a busca sempre que o usuário altera o valor do input
  onDateChange(newDate: string): void {
    this.selectedDate = newDate;
    this.fetchMeals();
  }

  // Navega para o dia anterior
  goPreviousDay(): void {
    const date = new Date(this.selectedDate);
    date.setDate(date.getDate() - 1);
    this.selectedDate = date.toISOString().split('T')[0];
    this.fetchMeals();
  }

  // Navega para o dia seguinte
  goNextDay(): void {
    const date = new Date(this.selectedDate);
    date.setDate(date.getDate() + 1);
    this.selectedDate = date.toISOString().split('T')[0];
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

  // Métodos para calcular a porcentagem de progresso
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
}
