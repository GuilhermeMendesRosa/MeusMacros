import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  // Valor fixo de calorias
  calories: number = 2000;

  // Porcentagens iniciais para cada macronutriente
  proteinPercent: number = 25;
  carbsPercent: number = 50;
  fatsPercent: number = 25;

  // Cálculo dos valores em gramas (arredondados)
  get proteinGrams(): number {
    return Math.round((this.proteinPercent / 100 * this.calories) / 4);
  }

  get carbsGrams(): number {
    return Math.round((this.carbsPercent / 100 * this.calories) / 4);
  }

  get fatsGrams(): number {
    return Math.round((this.fatsPercent / 100 * this.calories) / 9);
  }

  saveGoals() {
    // Exemplo: exibir os valores no console
    console.log('Metas salvas:');
    console.log(`Calorias: ${this.calories} kcal`);
    console.log(`Proteínas: ${this.proteinPercent}% -> ${this.proteinGrams}g`);
    console.log(`Carboidratos: ${this.carbsPercent}% -> ${this.carbsGrams}g`);
    console.log(`Gorduras: ${this.fatsPercent}% -> ${this.fatsGrams}g`);
  }

  public isDisabled(): boolean {
    return this.proteinPercent + this.carbsPercent + this.fatsPercent != 100;
  }

}
