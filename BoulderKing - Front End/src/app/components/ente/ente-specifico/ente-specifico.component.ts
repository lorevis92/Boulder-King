import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.interface';
import { EnteService } from 'src/app/services/ente.service';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';
import { Evento } from 'src/app/models/evento.interface';
import { EventoService } from 'src/app/services/evento.service';

@Component({
  selector: 'app-ente-specifico',
  templateUrl: './ente-specifico.component.html',
  styleUrls: ['./ente-specifico.component.scss']
})
export class EnteSpecificoComponent implements OnInit {
  page: number = 0;
  pageSize: number = 10;
  userData: any;
  userId!: string;
  ente!: User;
  id!: string;
  postResponse: any;
  dbImage: string = '';
  coordinate: { lat?: number, lng?: number } = { lat: 0, lng: 0 }; 
  visionaAggiungiInformazioni: boolean = false;
  visionaAggiungiOrari: boolean = false;
  visionaAggiungiTelefono: boolean = false;
  visionaAggiungiEmail: boolean = false;
  visionaAggiungiIndirizzo: boolean = false;
  visionaAggiungiSito: boolean = false;
  visionaAggiungiCoordinate: boolean = false;
  informazioni!: string;
  orari!: string;
  numeroTelefonico!: string;
  email!: string;
  indirizzo!: string;
  sito!: string;
  latitudine!: number;
  longitudine!: number;
  eventi: Evento[] = [];

  constructor(private eventoServ: EventoService, private userServ: UserService, private enteServ: EnteService, private route: ActivatedRoute, private router: Router, private httpClient: HttpClient) { }

  ngOnInit(): void {

    // Recupero dell'Id dell'ente dalla rotta parametrica
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.dettagliEnte();
  });

    // Recupero dell'id dell'utente loggato
  this.userData = this.userServ.getUserData();
  console.log(this.userData.sub);
  this.userId = this.userData.sub;
  }

  // CARICAMENTO DATI DI UN EVENTO SAPENDO IL SUO ID
  dettagliEnte(){
this.enteServ.getEnte(this.id).subscribe(ente => {
  this.ente = ente;
   // Aggiorna le coordinate con quelle dell'ente specifico
   this.coordinate = { lat: ente.latitudine, lng: ente.longitudine };
  this.viewImage();
  this.eventiStessoOrganizzatore();
});
  }

  // Richiama questa funzione per ottenere l'immagine dal database
  viewImage() {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    });
    const imageId = this.ente?.immagineProfilo?.id;
    console.log(imageId);
  
    try {
      if (imageId) {
        this.httpClient
          .get(`http://localhost:3001/get/image/${imageId}`, {
            responseType: 'blob',
            headers,
          })
          .subscribe(
            (res: Blob) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                this.dbImage = reader.result as string;
              };
              reader.readAsDataURL(res);
            },
            (error) => {
              console.error('Errore nel recupero dell\'immagine:', error);
            }
          );
      } else {
        console.error('ID dell\'immagine non valido.');
      }
    } catch (error) {
      console.error('Errore generale durante il recupero dell\'immagine:', error);
    }
  }
  
  aggiungiDati(){
    const enteToUpdate: User = {
      id: this.ente.id,
      email: this.email,
      orari: this.orari,
      numeroTelefonico: this.numeroTelefonico,
      sito: this.sito,
      informazioni: this.informazioni,
      indirizzo: this.indirizzo,
      latitudine: this.latitudine, 
      longitudine: this.longitudine,
    
    };
    this.userServ.updateUser(this.ente.id, enteToUpdate).subscribe(
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
  mostraAggiungiInformazioni(){
    this.visionaAggiungiInformazioni = !this.visionaAggiungiInformazioni;
  }

  mostraAggiungiOrari(){
    this.visionaAggiungiOrari = !this.visionaAggiungiOrari;
  }
  
  mostraAggiungiTelefono(){
    this.visionaAggiungiTelefono = !this.visionaAggiungiTelefono;
  }
  mostraAggiungiEmail(){
    this.visionaAggiungiEmail = !this.visionaAggiungiEmail;
  }
  mostraAggiungiIndirizzo(){
    this.visionaAggiungiIndirizzo = !this.visionaAggiungiIndirizzo;
  }
  mostraAggiungiSito(){
    this.visionaAggiungiSito = !this.visionaAggiungiSito;
  }
  mostraAggiungiCoordinate(){
    this.visionaAggiungiCoordinate = !this.visionaAggiungiCoordinate;
  }

    // EVENTI ORGANIZZATI DA UNO STESSO ORGANIZZATORE
    eventiStessoOrganizzatore(){
      this.eventoServ.getEventiByOrganizzatore(this.id, this.page, this.pageSize,'puntiClassifica').subscribe(eventi => {
        this.eventi = eventi;
      });
    }

}

