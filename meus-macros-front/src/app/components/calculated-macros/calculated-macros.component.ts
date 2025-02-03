import {Component} from '@angular/core';
import {ApexChart, ChartComponent} from 'ng-apexcharts';
import {CalculationService} from '../../services/calculation.service';
import {Meal} from '../../models/Meal';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-calculated-macros',
  templateUrl: './calculated-macros.component.html',
  imports: [
    ChartComponent,
    FormsModule
  ],
  styleUrls: ['./calculated-macros.component.css']
})
export class CalculatedMacrosComponent {


  protected readonly colors = ['#3B82F6', '#F97316', '#EF4444'];

  chartSeries: number[] = [];
  chartLabels = ['Proteínas', 'Carboidratos', 'Gorduras'];
  chartOptions: ApexChart = {
    type: 'pie',
    height: 250,
  };

  meal: Meal = {
    mealName: "",
    protein: 0,
    carbohydrates: 0,
    fat: 0,
    calories: 0,
    items: [],
  };

  constructor(
    private calculationsService: CalculationService,
    private router: Router
  ) {
  }

  ngOnInit() {
    if (this.calculationsService.meal) {
      this.meal = this.calculationsService.meal;
      this.updateChart();
      this.calculationsService.meal = undefined;
    }
  }

  private updateChart(): void {
    this.chartSeries = [
      this.meal.protein,
      this.meal.carbohydrates,
      this.meal.fat
    ];
  }

  public createMeal() {
    this.calculationsService.createMeal(this.meal).subscribe(value => {
      this.router.navigate(['/my-meals']);
    })
  }

}
