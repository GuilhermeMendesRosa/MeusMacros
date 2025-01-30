import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/User';
import {Observable} from 'rxjs';
import {AuthenticationTokens} from '../models/AuthenticationTokens';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8082/auth-meus-macros/auth'

  constructor(private http: HttpClient) {
  }

  public login(user: User): Observable<AuthenticationTokens> {
    return this.http.post<AuthenticationTokens>(`${this.API_URL}/login`, user);
  }
}
