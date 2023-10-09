import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user.interface';
import { EventoService } from 'src/app/services/evento.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-modifica-info-evento',
  templateUrl: './modifica-info-evento.component.html',
  styleUrls: ['./modifica-info-evento.component.scss']
})
export class ModificaInfoEventoComponent implements OnInit {

  eventoId!: string;
  nomeEvento!: string;
  immagineEvento!: string;
  data!: string;
  info!: string;
  sito!: string;
  citta!: string;
  provincia!: string;
  regione!: string;
  zonaItalia!: string;
  constructor(private eventoServ: EventoService, private userServ: UserService, private route: ActivatedRoute, private router: Router, private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.eventoId = params['id']; 
  });
  }

  aggiornaDati() {
    // Recupera l'utente esistente dal server
    this.eventoServ.getEvento(this.eventoId).subscribe(
      (evento) => {
        // Verifica e aggiorna solo i campi non nulli
        if (this.nomeEvento !== null && this.nomeEvento !== undefined) {
          evento.nomeEvento = this.nomeEvento;
        }
        if (this.immagineEvento !== null && this.immagineEvento !== undefined) {
          evento.immagineEvento = this.immagineEvento;
        }
        if (this.data !== null && this.data !== undefined) {
          evento.data = this.data;
        }
        if (this.citta !== null && this.citta !== undefined) {
          evento.citta = this.citta;
        }
        if (this.provincia !== null && this.provincia !== undefined) {
          evento.provincia = this.provincia;
        }
        if (this.regione !== null && this.regione !== undefined) {
          evento.regione = this.regione;
        }
        if (this.zonaItalia !== null && this.zonaItalia !== undefined) {
          evento.zonaItalia = this.zonaItalia;
        }
        if (this.info !== null && this.info !== undefined) {
          evento.info = this.info;
        }
        if (this.sito !== null && this.sito !== undefined) {
          evento.sito = this.sito;
        }
        
  
        // Esegui l'aggiornamento solo se almeno un campo è stato modificato
        if (
          this.nomeEvento !== null || this.immagineEvento !== null || this.data !== null ||
          this.citta !== null || this.provincia !== null || this.regione !== null ||
          this.zonaItalia !== null || this.info !== null || this.sito !== null
        ) {
          this.eventoServ.updateEvento(this.eventoId, evento).subscribe(
            (response) => {
              console.log('Evento modificato:', response);
  
              // Ricarica la pagina corrente
              this.router.routeReuseStrategy.shouldReuseRoute = () => false;
              this.router.onSameUrlNavigation = 'reload';
              this.router.navigate([this.router.url]);
            },
            (error) => {
              console.error('Errore durante l\'aggiornamento dell\'evento:', error);
            }
          );
        } else {
          console.log('Nessun campo è stato modificato.');
        }
      },
      (error) => {
        console.error('Errore nel recupero dell\'evento:', error);
      }
    );
  }
  

}
