import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'] // Changed from 'styleUrl' to 'styleUrls' and wrapped the path in an array
})
export class HomeComponent {

  constructor(private router: Router) {}

  onLoginClick() {
    this.router.navigate(['/login']);
  }

  onSignupClick() {
    this.router.navigate(['/signup']);
  }
}
