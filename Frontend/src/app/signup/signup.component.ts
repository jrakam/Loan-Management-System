import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  standalone: true,
  imports: [FormsModule,HeaderComponent, FooterComponent]
})
export class SignupComponent {
  // Add properties for form data
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
