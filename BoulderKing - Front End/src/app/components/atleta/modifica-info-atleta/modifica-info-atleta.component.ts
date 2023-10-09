import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-modifica-info-atleta',
  templateUrl: './modifica-info-atleta.component.html',
  styleUrls: ['./modifica-info-atleta.component.scss']
})
export class ModificaInfoAtletaComponent implements OnInit {
  user!: User;
  userData: any;
  userId!: string;
  name!: string;
  surname!: string;
  userName!: string;
  citta!: string;
  provincia!: string;
  regione!: string;
  zonaItalia!: string;
  
  constructor(private userServ: UserService, private router: Router) { }

  ngOnInit(): void {
    // Recupero dell'id dell'utente loggato
  this.userData = this.userServ.getUserData();
  console.log(this.userData.sub);
  this.userId = this.userData.sub;
  this.user = {} as User;
  }

  aggiornaDati() {
    // Recupera l'utente esistente dal server
    this.userServ.getUser(this.userId).subscribe(
      (user) => {
        // Verifica e aggiorna solo i campi non nulli
        if (this.name !== null && this.name !== undefined) {
          user.name = this.name;
        }
        if (this.surname !== null && this.surname !== undefined) {
          user.surname = this.surname;
        }
        if (this.userName !== null && this.userName !== undefined) {
          user.username = this.userName;
        }
        if (this.citta !== null && this.citta !== undefined) {
          user.citta = this.citta;
        }
        if (this.provincia !== null && this.provincia !== undefined) {
          user.provincia = this.provincia;
        }
        if (this.regione !== null && this.regione !== undefined) {
          user.regione = this.regione;
        }
        if (this.zonaItalia !== null && this.zonaItalia !== undefined) {
          user.zonaItalia = this.zonaItalia;
        }
  
        // Esegui l'aggiornamento solo se almeno un campo è stato modificato
        if (
          this.name !== null || this.surname !== null || this.userName !== null ||
          this.citta !== null || this.provincia !== null || this.regione !== null ||
          this.zonaItalia !== null
        ) {
          this.userServ.updateUser(this.userId, user).subscribe(
            (response) => {
              console.log('Ente modificato:', response);
  
              // Ricarica la pagina corrente
              this.router.routeReuseStrategy.shouldReuseRoute = () => false;
              this.router.onSameUrlNavigation = 'reload';
              this.router.navigate([this.router.url]);
            },
            (error) => {
              console.error('Errore durante l\'aggiornamento dell\'utente:', error);
            }
          );
        } else {
          console.log('Nessun campo è stato modificato.');
        }
      },
      (error) => {
        console.error('Errore nel recupero dell\'utente:', error);
      }
    );
  }
  

}
