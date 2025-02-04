import {Injectable} from '@angular/core';
import {Meal} from '../models/Meal';
import {HttpClient} from '@angular/common/http';
import {Transcription} from '../models/Transcription';
import {Observable} from 'rxjs';
import {Goal} from '../models/Goal';

@Injectable({
  providedIn: 'root'
})
export class GoalService {
  private readonly API_URL = 'http://192.168.1.171:8082/macros-calculator-meus-macros/goals'

  constructor(private http: HttpClient) {
  }

  public createGoal(goal: Goal): Observable<Goal> {
    return this.http.post<Goal>(`${this.API_URL}`, goal);
  }

  public getLatestGoal(): Observable<Goal> {
    return this.http.get<Goal>(`${this.API_URL}`);
  }
}
