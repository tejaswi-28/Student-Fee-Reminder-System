import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.css'
})
export class AdminHomeComponent {
  isDarkMode = true;

  toggleMode() {
    this.isDarkMode = !this.isDarkMode;
  }

  showWelcomeMessage = true; // Control the visibility of the welcome message

  constructor(private router: Router) {}

  ngOnInit() {
    // Check the route whenever the URL changes
    this.router.events.subscribe(() => {
      this.showWelcomeMessage = this.router.url === '/admin-home';
    });
  }

}
