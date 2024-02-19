import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): Observable<boolean> {
    console.log('AuthGuard#canActivate called'); // Log when canActivate is called

    return this.authService.checkSession().pipe(
      tap(isAuthenticated => {
        console.log('Session check:', isAuthenticated ? 'Authenticated' : 'Not Authenticated');
      }),
      map(isAuthenticated => {
        if (!isAuthenticated) {
          console.log('Not authenticated, redirecting to /login...');
          this.router.navigate(['/login']);
          return false;
        }
        console.log('Authenticated, allowing access...');
        return true;
      }),
      tap(isAllowed => {
        console.log('Access to route:', isAllowed ? 'Granted' : 'Denied');
      })
    );
  }
}
