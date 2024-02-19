

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { AuthService } from '../auth.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule, HeaderComponent, FooterComponent]
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.checkActiveSession();
  }

  checkActiveSession() {
    this.authService.checkSession().subscribe(isActive => {
      if (isActive) {
        this.router.navigate(['/file-upload']); // User already logged in, navigate away from login page
      }
    });
  }

  onSubmit() {
    this.isLoading = true;
    this.authService.login(this.username, this.password).subscribe(
      success => {
        this.isLoading = false;
        if (success) {
          this.router.navigate(['/file-upload']); // Adjust this route as necessary
        } else {
          this.errorMessage = 'Username or password is incorrect';
        }
      },
      error => {
        this.isLoading = false;
        this.errorMessage = 'An error occurred during login. Please try again later.';
      }
    );
  }
}

