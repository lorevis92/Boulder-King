import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.interface';
import { AtletaService } from 'src/app/services/atleta.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-atleta',
  templateUrl: './atleta.component.html',
  styleUrls: ['./atleta.component.scss']
})
export class AtletaComponent implements OnInit {
 
  page = 0; // Imposta la pagina iniziale
  pageSize =10;
  atleti: User[] = [];
  atleta!: User;
  dbImages: string[] = [];

  constructor(private atletaServ: AtletaService, private router: Router, private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.loadAtleti();
    setTimeout(() => {
      this.loadAtleti();
    }, 500);
  }

  // CARICA I PRIMI 10 ATLETI IN ORDINE DI POSIZIONAMENTO NELLA CLASSIFICA GLOBALE
  loadAtleti(): void{
    this.atletaServ.getAtletiPerPunteggio(this.page, this.pageSize, 'posizioneClassifica').subscribe((atleti: User[]) =>{
      console.log(this.atleti);
      this.atleti = atleti;
      this.loadImagesForAtleti();
    },
    (error) => {
      console.error("Error fetching users:", error);
    }
    );
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
  
}
