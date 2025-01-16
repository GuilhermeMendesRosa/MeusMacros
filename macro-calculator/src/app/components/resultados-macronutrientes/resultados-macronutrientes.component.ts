import { Component } from '@angular/core';
import { ApexChart, ApexLegend, ChartComponent } from 'ng-apexcharts'; // Importando os tipos corretos

@Component({
  selector: 'app-resultados-macronutrientes',
  templateUrl: './resultados-macronutrientes.component.html',
  imports: [
    ChartComponent
  ],
  styleUrls: ['./resultados-macronutrientes.component.css']
})
export class ResultadosMacronutrientesComponent {

  chartOptions = {
    series: [30, 25, 15], // Valores para o gráfico (proteínas, carboidratos, gorduras)
    chart: {
      type: 'pie' as ApexChart['type'], // Usando o tipo correto
    },
    labels: ['Proteínas', 'Carboidratos', 'Gorduras'],
    colors: ['#7C3AED', '#22C55E', '#FACC15'], // Cores personalizadas
    legend: {
      show: true,
      position: 'bottom' as ApexLegend['position'], // Usando o tipo correto para a posição
    },
    dataLabels: {
      enabled: true,
    },
  };
}
