import {MealItem} from './MealItem';

export interface Meal {
  id: number;
  mealName: string;
  calories: number;
  protein: number;
  carbohydrates: number;
  fat: number;
  items: MealItem[];
}
