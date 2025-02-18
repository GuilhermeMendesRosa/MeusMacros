import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {Goal} from '../models/Goal';

@Injectable({
  providedIn: 'root'
})
export class GoalService {
  private readonly API_URL = 'https://meus-macros-monolith-production.up.railway.app/goals'

  private _cachedGoal?: Goal;

  get cachedGoal(): Goal | undefined {
    return this._cachedGoal;
  }

  constructor(private http: HttpClient) {
  }

  public createGoal(goal: Goal): Observable<Goal> {
    return this.http.post<Goal>(`${this.API_URL}`, goal);
  }

  public getLatestGoal(): Observable<Goal> {
    return this.http.get<Goal>(`${this.API_URL}`).pipe(
      tap(goal => this._cachedGoal = goal)
    );
  }
}
