import { Component } from '@angular/core';
import { ApexChart, ApexLegend, ChartComponent } from 'ng-apexcharts'; // Importando os tipos corretos

@Component({
  selector: 'app-calculated-macros',
  templateUrl: './calculated-macros.component.html',
  imports: [
    ChartComponent
  ],
  styleUrls: ['./calculated-macros.component.css']
})
export class CalculatedMacrosComponent {
  chartSeries = [30, 25, 15];
  chartLabels = ['Proteínas', 'Carboidratos', 'Gorduras'];
  chartOptions: ApexChart = {
    type: 'pie',
    height: 250,
  };
}
