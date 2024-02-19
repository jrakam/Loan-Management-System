import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
 import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  standalone:true,
  imports:[CommonModule]
})
export class HeaderComponent implements OnInit {
  

  constructor(private router: Router, public authService: AuthService) {}

  ngOnInit(): void {
  }

  onHomeClick() {
    this.router.navigate(['']);
  }
  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']); 
      this.authService.isAuthenticated = false;
    }, error => {
      console.error('Logout failed', error);
      // Handle logout error
    });
  }
  
}