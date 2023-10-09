import { Component, OnInit, ViewChild } from '@angular/core';
import { Route, Router } from '@angular/router';
import { EventoService } from 'src/app/services/evento.service';
import { EventoComponent } from '../evento.component';
import { EnteService } from 'src/app/services/ente.service';
import { ToastComponent } from '../../toast/toast.component';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nuovo-evento',
  templateUrl: './nuovo-evento.component.html',
  styleUrls: ['./nuovo-evento.component.scss']
})
export class NuovoEventoComponent implements OnInit {
  page = 0; // Imposta la pagina iniziale
  size = 20;
  userData: any;
  userId!: string;
  organizzatoreInput: string = '';
  organizzatori: any[] = [];
  creatoreEvento: string ='';
  nomeEvento: string = '';
  localita: string = '';
  organizzatore: string = '';
  puntiEvento: string = '';
  data: string = '';
  immagineEvento: string = '';
  citta: string = '';
  provincia: string = '';
  regione: string = '';
  zonaItalia: string = '';
  info: string = '';
  sito: string = '';
  mostraTendina = true;
  toastRensponse: string = '';
  @ViewChild('errorToast') errorToast!: ToastComponent;

  constructor(private userServ: UserService, private eventoServ: EventoService, private enteServ: EnteService, private router: Router, private evento: EventoComponent) { }

  ngOnInit(): void {
    //VEdi chi è l'utente loggato per impostare chi sta creando l'evento
    this.userData = this.userServ.getUserData();
    console.log(this.userData.sub);
    this.userId = this.userData.sub;
    this.creatoreEvento = this.userId;
    console.log(this.creatoreEvento);
  }

  nuovoEvento(){
    if (!this.organizzatore) {
      this.toastRensponse = 'Per creare una nuova gara è necessario indicare il campo di battaglia';
      this.errorToast.open();
      return; // Esci dalla funzione se l'ID dell'organizzatore non è valido
    }
    this.eventoServ.nuovoEvento(this.nomeEvento, this.organizzatore, this.creatoreEvento, this.puntiEvento, this.data, this.immagineEvento, this.citta, this.provincia, this.regione, this.zonaItalia, this.info, this.sito).subscribe(
      (response) => {

        console.log('Nuovo evento creato:', response);
        this.toastRensponse = 'Nuovo evento creato!';
        this.errorToast.open();
        this.evento.mostraNuovoEvento = false;
        location.reload();
      },
      (error) => {
        this.toastRensponse = 'Il link di questa immagine supera i 10 mila caratteri e per questo non è possibile caricarla. Ti invitiamo a scegliere una foto con un percorso URL di dimensione inferiore'
        this.errorToast.open();
      }
    );
  }

  // Funzione di ricerca chiamata quando l'utente inserisce testo nell'input
searchOrganizzatori() {
  const nomeEnte = this.organizzatoreInput;
  if (nomeEnte && nomeEnte.length >= 3) { // Esegui la ricerca solo se sono stati inseriti almeno 3 caratteri
    this.enteServ.getEntiByNome(nomeEnte, this.page, this.size).subscribe((result) => {
      this.organizzatori = result;
    });
  } else {
    this.organizzatori = []; // Pulisci la lista se l'input è vuoto o ha meno di 3 caratteri
  }
}

// Funzione chiamata quando l'utente seleziona un'opzione dall'autocompletamento
selectOrganizzatore(organizzatore: any) {
  this.organizzatoreInput = organizzatore.nomeEnte;
  this.organizzatore = organizzatore.id;
  this.mostraTendina = false;
}


}
