import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/user.interface';
import { AtletaService } from 'src/app/services/atleta.service';
import { ToastComponent } from '../../toast/toast.component';

@Component({
  selector: 'app-classifica-principale',
  templateUrl: './classifica-principale.component.html',
  styleUrls: ['./classifica-principale.component.scss']
})
export class ClassificaPrincipaleComponent implements OnInit {
  page: number = 0; // Imposta la pagina iniziale
  pageSize: number = 100;
  totalPages: number = 0;
  atleti: User[] = [];
  @ViewChild('noNextPageToast') noNextPageToast!: ToastComponent;

  constructor(private atletaServ: AtletaService) { }
  

  ngOnInit(): void {
    this.loadAtleti();
  }

  // CARICAMENTO DI TUTTI GLI ATLETI IN ORDINE DI PUNTEGGIO
  loadAtleti(): void{
    this.atletaServ.getAtletiPerPunteggio(this.page, this.pageSize, 'email').subscribe((atleti: User[]) =>{
      console.log(this.atleti);
      this.atleti = atleti;
    },
    (error) => {
      console.error("Error fetching users:", error);
    }
    );
  }

  /*
  nextPage() {
    if (this.page < this.totalPages -1 ){
      this.page++; // Vai alla pagina successiva
      this.loadAtleti();
    }
    else {
      this.noNextPageToast.open();
    }
  }

  previousPage() {
      if (this.page < this.totalPages ) {
        this.page++; 
      this.loadAtleti();
    }
}
Non uso la paginazione, mostro solo i primi 100. Ora gestisco la posizione (ranking) come elemento che si genera da solo tramite il conteggio quando ordino l'array per il numero di punti. In futuro, ma gli attributi necessari sono già implementati, vorrei che la variabile Ranking fosse allineata con la posizione effettiva in maniera tale da poterla far vedere sulla pagina di ogni Atleta e di dare la possibilità di vedere la posizione anche degli atleti oltre la 100esima posizione  */
}
