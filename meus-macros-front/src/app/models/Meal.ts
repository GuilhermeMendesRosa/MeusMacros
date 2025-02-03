import {MealItem} from './MealItem';

export interface Meal {
  items: MealItem[];
  mealName: string;
  calories: number;
  protein: number;
  carbohydrates: number;
  fat: number;
}
