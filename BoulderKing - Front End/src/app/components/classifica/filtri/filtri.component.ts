import { Component, OnInit, ViewChild } from '@angular/core';
import { ClassificaService } from 'src/app/services/classifica.service';
import { Route, Router } from '@angular/router';
import { User } from 'src/app/models/user.interface';
import { AtletaService } from 'src/app/services/atleta.service';
import { ToastComponent } from '../../toast/toast.component';
import { EnteService } from 'src/app/services/ente.service';
@Component({
  selector: 'app-filtri',
  templateUrl: './filtri.component.html',
  styleUrls: ['./filtri.component.scss']
})
export class FiltriComponent implements OnInit {
  idEvento!:string;
  page: number = 0; 
  pageSize: number =100;
  totalPages: number = 0;
  atleti: User[] = [];
  regione!: string ;
  provincia!: string ;
  citta!: string ;
  zonaItalia!: string ;
  tipoEnte!: string;
  nomeEnte!: string;
  showClassifica: boolean = false;
  @ViewChild('noNextPageToast') noNextPageToast!: ToastComponent;
  
  constructor(private atletaServ: AtletaService, private classificaServ: ClassificaService, private router: Router) { }

  ngOnInit(): void {
    this.loadAtleti();
  }

// CARICAMENTO DI TUTTI GLI ATLETI IN ORDINE DI PUNTEGGIO
loadAtleti() {
  const filters: any = {
    citta: this.citta, // Usa '' se citta è undefined
    regione: this.regione, // Usa '' se regione è undefined
    provincia: this.provincia, // Usa '' se provincia è undefined
    nomeEnte: this.nomeEnte, // Usa '' se nomeEnte è undefined
    tipoEnte: this.tipoEnte, // Usa '' se tipoEnte è undefined
    zonaItalia: this.zonaItalia // Usa '' se zonaItalia è undefine
  };


  this.classificaServ.searchAtleti(filters, this.page, this.pageSize, 'puntiClassifica', 'desc')
  .subscribe((atleti: User[]) => {
    this.atleti = atleti;
    console.log(atleti);
  },
  (error) => {
    console.error("Error fetching users:", error);
  });
}

// Funzione che serve per innerscare i filtri quando premo il bottone
personalizza() {
  const filters: any = {
    citta: this.citta ? this.citta : null,
    regione: this.regione ? this.regione : null,
    provincia: this.provincia ? this.provincia : null,
    tipoEnte: this.tipoEnte ? this.tipoEnte : null,
    zonaItalia: this.zonaItalia ? this.zonaItalia : null
  };
  this.classificaServ.searchAtleti(filters, this.page, this.pageSize, 'puntiClassifica', 'desc')
  .subscribe((atleti: User[]) => {
    this.atleti = atleti;
    this.totalPages = Math.ceil(atleti.length / this.pageSize);
  },
  (error) => {
    console.error("Error fetching users:", error);
  });

  this.showClassifica = true;
}

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
}
