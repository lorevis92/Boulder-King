import { Component, OnInit, ViewChild, Renderer2} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Classifica } from 'src/app/models/classifica.interface';
import { Evento } from 'src/app/models/evento.interface';
import { User } from 'src/app/models/user.interface';
import { AtletaService } from 'src/app/services/atleta.service';
import { ClassificaService } from 'src/app/services/classifica.service';
import { EventoService } from 'src/app/services/evento.service';
import { UserService } from 'src/app/services/user.service';
import { ToastComponent } from '../../toast/toast.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-evento-specifico',
  templateUrl: './evento-specifico.component.html',
  styleUrls: ['./evento-specifico.component.scss']
})
export class EventoSpecificoComponent implements OnInit {
  page: number = 0; // Imposta la pagina iniziale
  pageSize: number = 6;
  pageEventi: number = 0;
  pageSizeEventi: number = 6;
  totalPagesStessoOrganizzatore: number = 0;
  totalPagesPartecipanti: number = 0;
  evento!: Evento;
  id!: string;
  atleti: User[]= [];
  eventiByOrganizzatore: Evento[] = [];
  mostraIscrizioneEvento: boolean = true;
  userData: any;
  userId!: string;
  userLoggato!: User;
  classifica!: Classifica;
  isClassificaPresent: boolean = false;
  visualizzaModificatore: boolean = false;
  eventi: Evento[] = [];
  dbImages: string[] = [];
  @ViewChild('noNextAtletiPageToast') noNextPageToast!: ToastComponent;
  @ViewChild('noNextEventiPageToast') noNextEventiPageToast!: ToastComponent;
  @ViewChild('successRegistrazioneToast') successRegistrazioneToast!: ToastComponent;
  @ViewChild('successClassificaToast') successClassificaToast!: ToastComponent;
  
  constructor(private renderer: Renderer2, private classificaServ: ClassificaService, private userServ: UserService, private atletaServ: AtletaService ,private eventoServ: EventoService, private route: ActivatedRoute, private router: Router, private httpClient: HttpClient) { }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      // Come si vede dal console log in UserService e da quello qui sotto l'id è salvato in userData.sub
      // Verifico se l'utente è loggato
    if (this.userServ.isAuthenticated()) {
      this.userData = this.userServ.getUserData();
      console.log(this.userData.sub);
      this.userId = this.userData.sub;
    }
      this.id = params['id'];
      this.dettagliEvento();
     // this.trovaClassificaByIdEvento();
      /*In questa funzione viene impostata la visibilità dell'iscrizione all'evento*/ 
      this.partecipantiEvento();
      this.eventiStessoOrganizzatore();
      this.dettagliUser;
      
  });
  }

  dettagliEvento(){
    this.eventoServ.getEvento(this.id).subscribe(evento => {
      this.evento = evento;
      this.trovaClassificaByIdEvento();
    });
  }

  eliminaEvento(){
    this.eventoServ.deleteEvento(this.id).subscribe();
alert('Evento eliminato con successo!');
    this.router.navigate(['/evento']);
  }

//PARTECIPANTI AD UN EVENTO SPECIFICO --> funziona ma non sta andando la paginazione?

  partecipantiEvento(){
    this.atletaServ.getAtletiPerEvento(this.id, this.page, this.pageSize,'puntiClassifica').subscribe(atleti => {
      this.atleti = atleti;
      console.log(atleti);
      this.loadImagesForAtleti();
      // Verifica se l'utente è tra i partecipanti
      const isUserParticipating = atleti.some(atleta => atleta.id === this.userId);
        
      // Imposta mostraIscrizioneEvento in base alla verifica
      this.mostraIscrizioneEvento = !isUserParticipating;
      console.log(this.mostraIscrizioneEvento);
      this.eventiStessoOrganizzatore(); // Ho messo la funzione qui in maniera che il server abbia il tempo di rispondere
      this.totalPagesPartecipanti = Math.ceil(atleti.length / this.pageSize);
      
    });
  }

  //PER OGNI ATLETA ESTRAE L'IMMAGINE PROFILO E LO METTE IN UN NUOVO ARRAY DA USARE LIBERAMENTE NEL TEMPLATE
  loadImagesForAtleti() {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });

    this.atleti.forEach((atleta, index) => {
      if (atleta?.immagineProfilo?.id) {
        // Assicurati di avere un'immagine da visualizzare
        this.httpClient.get('http://localhost:3001/get/image/' + atleta.immagineProfilo.id, { responseType: 'blob', headers })
          .subscribe(
            (res: Blob) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                this.dbImages[index] = reader.result as string;
                console.log(this.dbImages[index]);
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

  nextPage() {
    if (this.pageEventi < this.totalPagesPartecipanti - 1) {
      this.pageEventi++; // Vai alla pagina successiva solo se c'è una pagina successiva
      this.eventiStessoOrganizzatore();
    } else {
      this.noNextPageToast.open();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--; // Vai alla pagina precedente solo se non sei sulla prima pagina
      this.partecipantiEvento();
    }
  }

  // EVENTI ORGANIZZATI DA UNO STESSO ORGANIZZATORE
  eventiStessoOrganizzatore(){
    this.eventoServ.getEventiByOrganizzatore(this.evento.organizzatore?.id, this.pageEventi, this.pageSizeEventi,'puntiClassifica').subscribe(eventi => {
      this.eventiByOrganizzatore = eventi;
      console.log(eventi);
      this.totalPagesStessoOrganizzatore = Math.ceil(eventi.length / this.pageSizeEventi);
    });
  }
  
  scrollToTop() {
    // Fai tornare la pagina nella parte superiore
    window.scrollTo({ top: 0, behavior: 'smooth' }); // Questo farà scorrere la pagina gradualmente verso l'alto
  }
  

  nextPageOrganizzatore() {
    if (this.pageEventi < this.totalPagesStessoOrganizzatore - 1) {
      this.pageEventi++; // Vai alla pagina successiva solo se c'è una pagina successiva
      this.eventiStessoOrganizzatore();
    } else {
      this.noNextEventiPageToast.open();
    }
  }
  
  previousPageOrganizzatore() {
    if (this.pageEventi > 0) {
      this.pageEventi--; // Vai alla pagina precedente solo se non sei sulla prima pagina
      this.eventiStessoOrganizzatore();
    }
  }

  //Iscrizione ad un evento
  iscrizioneEvento(){
    this.eventoServ.iscrizioneEvento(this.userId, this.evento.id).subscribe(
      (response) => {

        console.log('Nuova partecipazione creata', response);
        this.successRegistrazioneToast.open();
        this.mostraIscrizioneEvento = false;
        console.log(this.mostraIscrizioneEvento);
        location.reload();
      },
      (error) => {
        console.error('Creation error:', error);
      }
    );
  }

  // CREA NUOVA CLASSIFICA PER L'EVENTO
  creaClassifica(){
    this.classificaServ.creaClassifica().subscribe(
      (response) => {
        console.log('Nuova classifica creata:', response);
        this.classifica = response;
        this.successClassificaToast.open();
        this.aggiornaClassifica(this.classifica.id, this.evento.id);
        location.reload();

        //this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Creation error:', error);
  
      }
    );
  }

  //AGGIORNA ID EVENTO DI UNA CLASSIFICA
  aggiornaClassifica(idClassifica:string, idEvento: string){
    this.classificaServ.aggiornaClassifica(idClassifica, idEvento).subscribe(
      (response) => {
        console.log('Classifica aggiornata:', response);
      },
      (error) => {
        console.error('Creation error:', error);
      }
    );
  }

  //TROVA CLASSIFICA CON ID DELL'EVENTO
  trovaClassificaByIdEvento() {
    // Verifica se l'utente è loggato
    if (this.userServ.isAuthenticated()) {
      // L'utente è loggato, puoi accedere alle sue proprietà
      this.userLoggato = this.userServ.getUserData();
      console.log('User loggato:', this.userLoggato);
    } else {
      // L'utente non è loggato, inizializza userLoggato come un oggetto vuoto o come preferisci
      this.userLoggato = {} as User;
    }
  
    this.classificaServ.getClassificaPerEvento(this.evento.id).subscribe(
      (response) => {
        console.log('Classifica response:', response, this.evento.id); // Controlla se la risposta contiene i dati attesi
        if (response && response.id) { 
          this.isClassificaPresent = true;
          this.classifica = response;
          console.log(this.isClassificaPresent);
          
        }
      },
      (error) => {
        console.error('Creation error:', error);
      }
    );
  }
  

  // Trova uno user dall'Id per poter definire se è un admin oppure no
  dettagliUser(){
    this.userServ.getUser(this.userId).subscribe(user => {
      this.userLoggato = user;
    });
  }

  visualizzaAggiornaDati(){
    this.visualizzaModificatore =! this.visualizzaModificatore;
  }

  
}
