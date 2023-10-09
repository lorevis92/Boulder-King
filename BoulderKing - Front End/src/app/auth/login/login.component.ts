import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ToastComponent } from 'src/app/components/toast/toast.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  email: string = '';
  password: string = '';
  emailError: string = '';
  errorResponse: string = '';
passwordError: string = '';
  @ViewChild('successToast') successToast!: ToastComponent;
  @ViewChild('errorToast') errorToast!: ToastComponent;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}
  
  login(): void {
  this.authService.login(this.email, this.password).subscribe(
    (response) => {
      // Login effettuato con successo
      const token = this.authService.getToken();
      console.log('Token:', token); // Verifica il token nella console
      this.successToast.open();
      this.router.navigate(['/']);
      console.log('Login effettuato:', response);
    },
    (error) => {
      if (error.status === 404) {
        // Errore di autenticazione
        this.errorResponse = 'Mail sbagliata, controlla eventuali errori di battitura!';
       } else if (this.errorResponse) {
          this.errorResponse = 'Password sbagliata, riprova oppure cambia password!';
        } else {
          this.errorResponse = 'Password sbagliata, riprova con la password corretta oppure cambia password!';
        }
      this.errorToast.open();
      console.error('Errore di login:', error);
    }
  );
}

}
