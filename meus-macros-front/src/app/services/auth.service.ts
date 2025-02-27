import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/User';
import {Observable, tap} from 'rxjs';
import {AuthenticationTokens} from '../models/AuthenticationTokens';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'https://meusmacros-production.up.railway.app/auth';
  private readonly TOKEN_KEY = 'auth_token';

  private _cachedUser?: User;

  get cachedUser(): User | undefined {
    return this._cachedUser;
  }

  constructor(private http: HttpClient) {
  }

  public login(user: User): Observable<AuthenticationTokens> {
    return this.http.post<AuthenticationTokens>(`${this.API_URL}/login`, user).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem(this.TOKEN_KEY, response.token);
        }
      })
    );
  }

  public register(user: User): Observable<AuthenticationTokens> {
    return this.http.post<AuthenticationTokens>(`${this.API_URL}/register`, user);
  }

  public me(): Observable<User> {
    return this.http.get<User>(`${this.API_URL}/me`).pipe(
      tap(user => this._cachedUser = user)
    );
  }

  public getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  public logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this._cachedUser = undefined;
  }
}
