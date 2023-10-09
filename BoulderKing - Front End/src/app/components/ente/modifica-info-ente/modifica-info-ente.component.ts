import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-modifica-info-ente',
  templateUrl: './modifica-info-ente.component.html',
  styleUrls: ['./modifica-info-ente.component.scss']
})
export class ModificaInfoEnteComponent implements OnInit {
  user!: User;
  userData: any;
  userId!: string;
  nomeEnte!: string;
  orari!: string;
  telefono!: string;
  sito!: string;
  informazioni!: string;
  citta!: string;
  provincia!: string;
  regione!: string;
  zonaItalia!: string;
  falesiavspalestra!: string;
  indirizzo!: string;
  latitudine!: number;
  latEffettiva!: number;
  longitudine!: number;

  constructor(private userServ: UserService, private router: Router) { }

  ngOnInit(): void {
    // Recupero dell'id dell'utente loggato
  this.userData = this.userServ.getUserData();
  console.log(this.userData.sub);
  this.userId = this.userData.sub;
  this.user = {} as User;
  }

  aggiornaDati() {
    const userToUpdate: User = {
      id: this.userId, // Id dell'utente da aggiornare
      email: this.user.email,
      nomeEnte: this.nomeEnte,
      orari: this.orari,
      numeroTelefonico: this.telefono,
      sito: this.sito,
      informazioni: this.informazioni,
      citta: this.citta,
      provincia: this.provincia,
      regione: this.regione,
      zonaItalia: this.zonaItalia,
      tipoEnte: this.falesiavspalestra, // Assumendo che 'tipoEnte' rappresenti la scelta tra 'falesia' e 'palestra'
      indirizzo: this.indirizzo,
      latitudine: this.latitudine !== undefined ? this.latitudine : this.user.latitudine, // Usa il valore precedente se latitudine non è stato inserito
      longitudine: this.longitudine !== undefined ? this.longitudine : this.user.longitudine,// Aggiungi il campo falesiavspalestra
    
    };
    this.userServ.updateUser(this.userId, userToUpdate).subscribe(
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
    }

}
