import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private encodedCredentials: string | null = null;

  setCredentials(username: string, password: string) {
    this.encodedCredentials = btoa(username + ':' + password);
  }

  getEncodedCredentials(): string | null {
    return this.encodedCredentials;
  }
}
