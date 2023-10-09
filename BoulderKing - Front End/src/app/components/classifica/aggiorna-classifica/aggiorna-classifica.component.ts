import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Evento } from 'src/app/models/evento.interface';
import { User } from 'src/app/models/user.interface';
import { AtletaService } from 'src/app/services/atleta.service';
import { ClassificaService } from 'src/app/services/classifica.service';

@Component({
  selector: 'app-aggiorna-classifica',
  templateUrl: './aggiorna-classifica.component.html',
  styleUrls: ['./aggiorna-classifica.component.scss']
})
export class AggiornaClassificaComponent implements OnInit {
  page: number = 0;
  size: number = 10;
  id!: string;
  posizione01: string = '';
  posizione02: string = '';
  posizione03: string = '';
  posizione04: string = '';
  posizione05: string = '';
  posizione06: string = '';
  posizione07: string = '';
  posizione08: string = '';
  posizione09: string = '';
  posizione10: string = '';
  atleta01: string = '';
  atleta02: string = '';
  atleta03: string = '';
  atleta04: string = '';
  atleta05: string = '';
  atleta06: string = '';
  atleta07: string = '';
  atleta08: string = '';
  atleta09: string = '';
  atleta10: string = '';

  atleti01: any[] = [];
  atleti02: any[] = [];
  atleti03: any[] = [];
  atleti04: any[] = [];
  atleti05: any[] = [];
  atleti06: any[] = [];
  atleti07: any[] = [];
  atleti08: any[] = [];
  atleti09: any[] = [];
  atleti10: any[] = [];
  mostraTendina01 = true;
  mostraTendina02 = true;
  mostraTendina03 = true;
  mostraTendina04 = true;
  mostraTendina05 = true;
  mostraTendina06 = true;
  mostraTendina07 = true;
  mostraTendina08 = true;
  mostraTendina09 = true;
  mostraTendina10 = true;
  evento!: Evento;

  constructor(private atletaServ: AtletaService, private classificaServ: ClassificaService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
    this.id = params['id'];
    console.log(this.id);
});

//CHIAMO QUESTA FUNZIONE ON INIT PER AVERE A DISPOSIZIONE L'ID DELL'EVENTO DA USARE NELLA NAVIGAZIONE
  this.trovaEventoByClassifica();
}

//FUNZIONE PER FARE LA PUT E MODIFICARE UNA CLASSIFICA GIà CREATA
  aggiornaPosizioniClassifica(){
    this.classificaServ.aggiornaPosizioniClassifica(this.id, this.posizione01, this.posizione02, this.posizione03, this.posizione04, this.posizione05, this.posizione06, this.posizione07, this.posizione08, this.posizione09, this.posizione10).subscribe(
      (response) => {

        console.log('Classifica aggiornata:', response);
        alert('Classifica aggiornata!');
        this.router.navigate(['/classifica-evento', this.evento.id]);
      },
      (error) => {
        console.error('Creation error:', error);
      }
    );
  }


// FUNZIONE PER TROVARE UN EVENTO ASSOCIATO AD UNA CLASSIFICA
trovaEventoByClassifica(){
  this.classificaServ.trovaEvento(this.id).subscribe(evento => {
    this.evento = evento;
  });
}

//FUNZIONE PER CERCARE GLI ALTLETI DISPONIBILI
searchAtleta01() {
  const cognomeAtleta01 = this.atleta01;
  if (cognomeAtleta01 && cognomeAtleta01.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta01, this.page).subscribe((result) => {
      this.atleti01 = result;
      this.mostraTendina01 = true;
    });
  } else {
    this.atleti01 = [];
    this.mostraTendina01 = false;
  }
}

searchAtleta02() {
  const cognomeAtleta02 = this.atleta02;
  if (cognomeAtleta02 && cognomeAtleta02.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta02, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti02 = result;
      this.mostraTendina02 = true;
    });
  } else {
    this.atleti02 = [];
    this.mostraTendina02 = false;
  }
}

searchAtleta03() {
  const cognomeAtleta03 = this.atleta03;
  if (cognomeAtleta03 && cognomeAtleta03.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta03, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti03 = result;
      this.mostraTendina03 = true;
    });
  } else {
    this.atleti03 = [];
    this.mostraTendina03 = false;
  }
}

searchAtleta04() {
  const cognomeAtleta04 = this.atleta04;
  if (cognomeAtleta04 && cognomeAtleta04.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta04, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti04 = result;
      this.mostraTendina04 = true;
    });
  } else {
    this.atleti04 = [];
    this.mostraTendina04 = false;
  }
}

searchAtleta05() {
  const cognomeAtleta05 = this.atleta05;
  if (cognomeAtleta05 && cognomeAtleta05.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta05, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti05 = result;
      this.mostraTendina05 = true;
    });
  } else {
    this.atleti05 = [];
    this.mostraTendina05 = false;
  }
}

searchAtleta06() {
  const cognomeAtleta06 = this.atleta06;
  if (cognomeAtleta06 && cognomeAtleta06.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta06, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti06 = result;
      this.mostraTendina06 = true;
    });
  } else {
    this.atleti06 = [];
    this.mostraTendina06 = false;
  }
}

searchAtleta07() {
  const cognomeAtleta07 = this.atleta07;
  if (cognomeAtleta07 && cognomeAtleta07.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta07, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti07 = result;
      this.mostraTendina07 = true;
    });
  } else {
    this.atleti07 = [];
    this.mostraTendina07 = false;
  }
}

searchAtleta08() {
  const cognomeAtleta08 = this.atleta08;
  if (cognomeAtleta08 && cognomeAtleta08.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta08, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti08 = result;
      this.mostraTendina08 = true;
    });
  } else {
    this.atleti08 = [];
    this.mostraTendina08 = false;
  }
}

searchAtleta09() {
  const cognomeAtleta09 = this.atleta09;
  if (cognomeAtleta09 && cognomeAtleta09.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta09, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti09 = result;
      this.mostraTendina09 = true;
    });
  } else {
    this.atleti09 = [];
    this.mostraTendina09 = false;
  }
}

searchAtleta10() {
  const cognomeAtleta10 = this.atleta10;
  if (cognomeAtleta10 && cognomeAtleta10.length >= 1) {
    this.atletaServ.getAtletaPerCognome(cognomeAtleta10, this.page).subscribe((result) => {
      console.log('Risultati atleta:', result); // Controlla i risultati nella console
      this.atleti10 = result;
      this.mostraTendina10 = true;
    });
  } else {
    this.atleti10 = [];
    this.mostraTendina10 = false;
  }
}



// Funzione chiamata quando l'utente seleziona un'opzione dall'autocompletamento
selectAtleta01(atleta01: any) {
  this.atleta01 = atleta01.cognome; // Imposta il cognome dell'atleta01 selezionato nell'input "Atleta01 1"
  this.atleta01 = atleta01.surname + ' ' + atleta01.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione01 = atleta01.id;
  this.mostraTendina01 = false; // Nascondi la tendina dopo la selezione
}

selectAtleta02(atleta02: any) {
  this.atleta02 = atleta02.cognome; 
  this.atleta02 = atleta02.surname + ' ' + atleta02.name;
  this.posizione02 = atleta02.id;  // Correggi qui
  this.mostraTendina02 = false;
}
selectAtleta03(atleta03: any) {
  this.atleta03 = atleta03.cognome; // Imposta il cognome dell'atleta03 selezionato nell'input "Atleta03 1"
  this.atleta03 = atleta03.surname + ' ' + atleta03.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione03 = atleta03.id;
  this.mostraTendina03 = false; // Nascondi la tendina dopo la selezione
}
selectAtleta04(atleta04: any) {
  this.atleta04 = atleta04.cognome; // Imposta il cognome dell'atleta04 selezionato nell'input "Atleta04 1"
  this.atleta04 = atleta04.surname + ' ' + atleta04.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione04 = atleta04.id;
  this.mostraTendina04 = false; // Nascondi la tendina dopo la selezione
}
selectAtleta05(atleta05: any) {
  this.atleta05 = atleta05.cognome; // Imposta il cognome dell'atleta05 selezionato nell'input "Atleta05 1"
  this.atleta05 = atleta05.surname + ' ' + atleta05.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione05 = atleta05.id;
  this.mostraTendina05 = false; // Nascondi la tendina dopo la selezione
}
selectAtleta06(atleta06: any) {
  this.atleta06 = atleta06.cognome; // Imposta il cognome dell'atleta06 selezionato nell'input "Atleta06 1"
  this.atleta06 = atleta06.surname + ' ' + atleta06.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione06 = atleta06.id;
  this.mostraTendina06 = false; // Nascondi la tendina dopo la selezione
}
selectAtleta07(atleta07: any) {
  this.atleta07 = atleta07.cognome; // Imposta il cognome dell'atleta07 selezionato nell'input "Atleta07 1"
  this.atleta07 = atleta07.surname + ' ' + atleta07.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione07 = atleta07.id;
  this.mostraTendina07 = false; // Nascondi la tendina dopo la selezione
}
selectAtleta08(atleta08: any) {
  this.atleta08 = atleta08.cognome; // Imposta il cognome dell'atleta08 selezionato nell'input "Atleta08 1"
  this.atleta08 = atleta08.surname + ' ' + atleta08.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione08 = atleta08.id;
  this.mostraTendina08 = false; // Nascondi la tendina dopo la selezione
}
selectAtleta09(atleta09: any) {
  this.atleta09 = atleta09.cognome; // Imposta il cognome dell'atleta09 selezionato nell'input "Atleta09 1"
  this.atleta09 = atleta09.surname + ' ' + atleta09.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione09 = atleta09.id;
  this.mostraTendina09 = false; // Nascondi la tendina dopo la selezione
}
selectAtleta10(atleta10: any) {
  this.atleta10 = atleta10.cognome; // Imposta il cognome dell'atleta10 selezionato nell'input "Atleta10 1"
  this.atleta10 = atleta10.surname + ' ' + atleta10.name; // Serve per far si che rimanga identificato nel campo di input
  this.posizione10 = atleta10.id;
  this.mostraTendina10 = false; // Nascondi la tendina dopo la selezione
}


}
