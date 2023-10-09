import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../auth.service';
import { Route, Router } from '@angular/router';
import { ToastComponent } from 'src/app/components/toast/toast.component';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  username: string = '';
  password: string = '';
  nome :string = '';
  cognome: string = '';
  tipoUser: string = 'ATLETA';
  nomeEnte: string = '';
  email :string = '';
@ViewChild('successToast') successToast!: ToastComponent;
@ViewChild('errorToast') errorToast!: ToastComponent;
  
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

register(){
  this.authService.register(this.username, this.nomeEnte, this.nome, this.cognome, this.email, this.password, this.tipoUser).subscribe(
    (response) => {
      this.successToast.open();
      console.log('Registrazione effettuata:', response);
      this.router.navigate(['/login']);
    },
    (error) => {
      console.error('Registration error:', error);
      this.errorToast.open();
    }
  );
}
}



