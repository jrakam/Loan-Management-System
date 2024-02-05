import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {

  username: string = '';
  email: string = '';
  password: string = '';

  constructor() { }

  // Method to handle form submission
  onSignup() {
    console.log('Signup:', this.username, this.email, this.password);
    // Add logic to handle signup
  }
}
