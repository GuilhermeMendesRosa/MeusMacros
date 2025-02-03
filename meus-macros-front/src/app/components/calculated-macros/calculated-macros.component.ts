import {Component} from '@angular/core';
import {ApexChart, ChartComponent} from 'ng-apexcharts';
import {CalculationService} from '../../services/calculation.service';
import {Meal} from '../../models/Meal';

@Component({
  selector: 'app-calculated-macros',
  templateUrl: './calculated-macros.component.html',
  imports: [
    ChartComponent
  ],
  styleUrls: ['./calculated-macros.component.css']
})
export class CalculatedMacrosComponent {

  chartSeries: number[] = [];
  chartLabels = ['Proteínas', 'Carboidratos', 'Gorduras'];
  chartOptions: ApexChart = {
    type: 'pie',
    height: 250,
  };

  meal: Meal = {
    protein: 0,
    carbohydrates: 0,
    fat: 0,
    calories: 0,
    items: [],
  };

  constructor(private calculationsService: CalculationService) {}

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
}
