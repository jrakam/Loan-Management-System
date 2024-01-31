import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [FormsModule, HttpClientModule]
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  apiUrl: string = 'http://localhost:8080/api/login'; // URL of your Spring Boot backend

  constructor(private http: HttpClient) { }

  onSubmit() {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)
    });

    this.http.get(this.apiUrl, { headers, responseType: 'text', withCredentials: true }).subscribe(
      response => {
        console.log('Success:', response);
        // Handle successful authentication here
      },
      error => {
        console.error('Error:', error);
        // Handle authentication failure here
      }
    );
  }
}
