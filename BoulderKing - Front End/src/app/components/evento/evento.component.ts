import { Component, OnInit } from '@angular/core';
import { Evento } from 'src/app/models/evento.interface';
import { EventoService } from 'src/app/services/evento.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-evento',
  templateUrl: './evento.component.html',
  styleUrls: ['./evento.component.scss']
})
export class EventoComponent implements OnInit {
  userData: any;
  userId!: string;
  mostraNuovoEvento: boolean = false;
  page: number = 0; // Imposta la pagina iniziale
  size: number = 9;
  sizeSearch: number = 100;
  eventi: Evento[] = [];
  eventiFiltrati: Evento[] = [];
  citta!: string ;
  provincia!: string ;
  regione!: string ;
  zonaItalia!: string ;
  isPassed!: string ;
  totalPages: number = 0;
  nomeEvento!: string;
  nomeEnte!: string;
  date!: string;
  mostraEventiPersonalizzati: boolean = false;

  constructor(private eventoServ: EventoService, private userServ: UserService) { }

  ngOnInit(): void {
    this.loadEntiFuturi();
    setTimeout(() => {
      this.loadEntiFuturi();
    }, 500);

    //Ricava l'id dell'utente collegato --> mi serve per verificare se mostrare il div per creare il nuovo evento
    this.userData = this.userServ.getUserData();
    console.log(this.userData.sub);
    this.userId = this.userData.sub;
  }

  //CARICAMENTO DI TUTTI GLI EVENTI
  loadEventi(): void{
    this.eventoServ.getEventi(this.page, this.size,'email').subscribe((eventi: Evento[]) =>{
      console.log(this.eventi);
      this.eventi = eventi;
    },
    (error) => {
      console.error("Error fetching users:", error);
    }
    );
  }



  personalizza() {
    const filters: any = {
      citta: this.citta ? this.citta : null,
      regione: this.regione ? this.regione : null,
      provincia: this.provincia ? this.provincia : null,
      nomeEvento: this.nomeEvento ? this.nomeEvento : null,
      nomeEnte: this.nomeEnte ? this.nomeEnte : null,
      zonaItalia: this.zonaItalia ? this.zonaItalia : null,
      isPassed: this.isPassed ? this.isPassed:null,
      date: this.date ? this.date:null
    };
  
    this.eventoServ.searchEventi(filters, this.page, this.sizeSearch)
    .subscribe((eventi: Evento[]) => {
      this.eventiFiltrati = eventi;
      console.log(this.eventiFiltrati);
      // this.totalPages = Math.ceil(eventi.length / this.size); FORMULA NON CORRETTA. Però anche andando a prendere il valore dal JSON dell'API non mi ritorna come un dato utile (NaN o Undefined). Anche se questa gestione del flusso mi piace, sarebbe bello riuscire a risolvere il problema
    },
    (error) => {
      console.error("Error fetching users:", error);
    });
    this.mostraEventiPersonalizzati = true;
  }

  loadEntiFuturi() {
    const filters: any = {
      citta: null,
      regione: null,
      provincia: null,
      nomeEvento: null,
      nomeEnte: null,
      zonaItalia: null,
      isPassed: 'FUTURO',
      date: null
    };
  
    this.eventoServ.searchEventi(filters, this.page, this.sizeSearch)
    .subscribe((eventi: Evento[]) => {
      this.eventi = eventi;;
      console.log(this.eventi);
      // this.totalPages = Math.ceil(eventi.length / this.size); FORMULA NON CORRETTA. Però anche andando a prendere il valore dal JSON dell'API non mi ritorna come un dato utile (NaN o Undefined). Anche se questa gestione del flusso mi piace, sarebbe bello riuscire a risolvere il problema
    },
    (error) => {
      console.error("Error fetching users:", error);
    });
  }

}
