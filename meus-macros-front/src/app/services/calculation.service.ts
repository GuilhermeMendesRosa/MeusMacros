import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Transcription} from '../models/Transcription';
import {Meal} from '../models/Meal';

@Injectable({
  providedIn: 'root'
})
export class CalculationService {
  private readonly API_URL = 'http://192.168.1.171:8082/macros-calculator-meus-macros'

  public meal?: Meal;

  constructor(private http: HttpClient) {
  }

  public calculateMeal(transcription: Transcription): Observable<Meal> {
    return this.http.post<Meal>(`${this.API_URL}/calculate`, transcription);
  }

  public createMeal(meal: Meal): Observable<string> {
    return this.http.post<string>(`${this.API_URL}/meals/create`, meal);
  }

  public listMeals(date: string): Observable<Meal[]> {
    return this.http.post<Meal[]>(`${this.API_URL}/meals/list`, {date});
  }

}
