import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Goal} from '../models/Goal';

@Injectable({
  providedIn: 'root'
})
export class GoalService {
  private readonly API_URL = 'http://localhost:8082/macros-calculator-meus-macros/goals'

  constructor(private http: HttpClient) {
  }

  public createGoal(goal: Goal): Observable<Goal> {
    return this.http.post<Goal>(`${this.API_URL}`, goal);
  }

  public getLatestGoal(): Observable<Goal> {
    return this.http.get<Goal>(`${this.API_URL}`);
  }
}
