import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Transcription} from '../models/Transcription';
import {Meal} from '../models/Meal';

@Injectable({
  providedIn: 'root'
})
export class CalculationService {
  private readonly API_URL = 'http://localhost:8082/macros-calculator-meus-macros'

  constructor(private http: HttpClient) {
  }

  public login(user: Transcription): Observable<Meal> {
    return this.http.post<Meal>(`${this.API_URL}/calculate`, user);
  }
}
