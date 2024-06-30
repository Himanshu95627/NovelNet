import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {
  message: string = '';
  isOkey: boolean = false;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) { }

  onCodeCompleted(token: string) {
    this.confirmAccount(token);

  }
  confirmAccount(token: string) {
    this.authService.confirmAccount(
      { token }
    ).subscribe({
      next: () => {
        this.message = 'Your account has been activated successfully.\nNow you can proceed to login.';
        this.submitted = true;
        this.isOkey = true;
      },
      error: () => {
        this.message = 'Invalid or expired token';
        this.submitted = true;
        this.isOkey = false;
      }
    })
  }
  redirectToLogin() {
    this.router.navigate(['login']);
  }
}
