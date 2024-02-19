
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080'; // Adjust according to your backend API's base URL
  public isAuthenticated: boolean = false;

  constructor(private http: HttpClient, private router: Router) {}


  login(username: string, password: string): Observable<boolean> {
    // Prepare form-encoded data
    this.isAuthenticated = true;
    const body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);

    return this.http.post(`${this.baseUrl}/perform_login`, body.toString(), {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      }),
      withCredentials: true,
      observe: 'response',
      responseType: 'text'
    }).pipe(
      tap(response => {
        console.log('Login response:', response);
      }),
      map(response => {
        const loginSuccess = !response.url?.includes('login?error');
        console.log('Login success:', loginSuccess);
        return loginSuccess;
      }),
      catchError(error => {
        console.error('Login error:', error);
        return of(false);
      })
    );
  }
  
  
  logout(): Observable<string> {
    return this.http.post(`${this.baseUrl}/logout`, {}, {
      withCredentials: true,
      observe: 'response',
      responseType: 'text' // Correctly expect a text response
      
    }).pipe(
      map(response => response.body ? response.body.toString() : ''),
      catchError(error => {
        console.error('Logout failed', error);
        return of(''); // Return an observable of an empty string on error
      })
    );
  }
  
  
  
  checkSession(): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/check-session`, { withCredentials: true })
      .pipe(
        tap(isActive => this.isAuthenticated = isActive),
        catchError(() => of(false))
      );
  }

}
