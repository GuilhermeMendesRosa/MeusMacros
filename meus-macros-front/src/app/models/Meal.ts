import {MealItem} from './MealItem';

export interface Meal {
  items: MealItem[];
  calories: number;
  protein: number;
  carbohydrates: number;
  fat: number;
}
