import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.interface';
import { EnteService } from 'src/app/services/ente.service';
import { ToastComponent } from '../toast/toast.component';
import { AuthService } from 'src/app/auth/auth.service';
import { UserService } from 'src/app/services/user.service';
import { Evento } from 'src/app/models/evento.interface';
import { EventoService } from 'src/app/services/evento.service';

@Component({
  selector: 'app-ente',
  templateUrl: './ente.component.html',
  styleUrls: ['./ente.component.scss']
})
export class EnteComponent implements OnInit {
  userData: any;
  userId!: string;
  nomeEnte!: string ;
  regione!: string ;
  provincia!: string ;
  citta!: string ;
  zonaItalia!: string ;
  tipoEnte!: string;
  page: number = 0; // Imposta la pagina iniziale
  size: number = 100;
  enti: User[] = [];
  ente!: User;
  dbImages: string[] = [];
  totalPages: number = 0;
  //query: string = '';
  @ViewChild('noNextPageToast') noNextPageToast!: ToastComponent;

  constructor(private enteServ: EnteService, private userServ: UserService, private authServ: AuthService, private router: Router, private httpClient: HttpClient) { }

  ngOnInit(): void {

  //SENZA TIME OUT CI SONO PROBLEMI DI ASINCRONICITà
    setTimeout(() => {
      this.loadEnti();
    }, 500);

  // Recupero dell'id dell'utente loggato
  this.userData = this.userServ.getUserData();
  console.log(this.userData.sub);
  this.userId = this.userData.sub;
  }

  
  loadEnti() {
    const filters: any = {
      citta: this.citta, // Usa '' se citta è undefined
      regione: this.regione, // Usa '' se regione è undefined
      provincia: this.provincia, // Usa '' se provincia è undefined
      nomeEnte: this.nomeEnte, // Usa '' se nomeEnte è undefined
      tipoEnte: this.tipoEnte, // Usa '' se tipoEnte è undefined
      zonaItalia: this.zonaItalia // Usa '' se zonaItalia è undefine
    };
  
    this.enteServ.searchEnti(filters, this.page, this.size, 'nomeEnte')
    .subscribe((enti: User[]) => {
      this.enti = enti;
      this.loadImagesForEnti();
      this.totalPages = Math.ceil(enti.length / this.size);
    },
    (error) => {
      console.error("Error fetching users:", error);
    });
  }


  nextPage() {
   // if (this.page < this.totalPages ) {
      this.page++;
      this.loadEnti();
    //} else {
     // this.noNextPageToast.open();
    //}
  }

  previousPage() {
    if (this.page > 0) {
      this.page--; // Vai alla pagina precedente solo se non sei sulla prima pagina
      this.loadEnti();
    }
  }

  personalizza() {
    const filters: any = {
      citta: this.citta ? this.citta : null,
      regione: this.regione ? this.regione : null,
      provincia: this.provincia ? this.provincia : null,
      nomeEnte: this.nomeEnte ? this.nomeEnte : null,
      tipoEnte: this.tipoEnte ? this.tipoEnte : null,
      zonaItalia: this.zonaItalia ? this.zonaItalia : null
    };
  
    this.enteServ.searchEnti(filters, this.page, this.size, 'nomeEnte')
    .subscribe((enti: User[]) => {
      this.enti = enti;
      this.loadImagesForEnti();
      this.totalPages = Math.ceil(enti.length / this.size);
    },
    (error) => {
      console.error("Error fetching users:", error);
    });
  }

  logout() {
    if(this.userId){
      this.authServ.logout();
      this.router.navigate(['/login']);
      console.log('Logout effettuato con successo');
    }
  }

  loadImagesForEnti() {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });

    this.enti.forEach((ente, index) => {
      if (ente?.immagineProfilo?.id) {
        // Assicurati di avere un'immagine da visualizzare
        this.httpClient.get('http://localhost:3001/get/image/' + ente.immagineProfilo.id, { responseType: 'blob', headers })
          .subscribe(
            (res: Blob) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                this.dbImages[index] = reader.result as string;
              };
              reader.readAsDataURL(res);
            },
            (error) => {
              console.error('Errore nel recupero dell\'immagine:', error);
            }
          );
      } else {
        this.dbImages[index] = ''; // Imposta una stringa vuota se non c'è un'immagine
      }
    });
  }

 
  
}