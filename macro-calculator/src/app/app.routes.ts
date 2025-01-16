import {Routes} from '@angular/router';
import {
  CalcularMacronutrientesComponent
} from './components/calcular-macronutrientes/calcular-macronutrientes.component';
import {
  ResultadosMacronutrientesComponent
} from './components/resultados-macronutrientes/resultados-macronutrientes.component';
import {MinhasRefeicoesComponent} from './components/minhas-refeicoes/minhas-refeicoes.component';

export const routes: Routes = [
  {path: '', redirectTo: 'calcular', pathMatch: 'full'},
  {path: 'calcular', component: CalcularMacronutrientesComponent},
  {path: 'resultados', component: ResultadosMacronutrientesComponent},
  {path: 'refeicoes', component: MinhasRefeicoesComponent},];
