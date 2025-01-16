import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-calcular-macronutrientes',
  templateUrl: './calcular-macronutrientes.component.html',
  styleUrls: ['./calcular-macronutrientes.component.css']
})
export class CalcularMacronutrientesComponent {
  constructor(private router: Router) {}

  navigateToResultados() {
    this.router.navigate(['/resultados']);
  }
}
