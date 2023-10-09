import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user.interface';
import { AtletaService } from 'src/app/services/atleta.service';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-atleta-specifico',
  templateUrl: './atleta-specifico.component.html',
  styleUrls: ['./atleta-specifico.component.scss']
})
export class AtletaSpecificoComponent implements OnInit {
  userData: any;
  userId!: string;
  atleta!: User;
  id!: string;
  postResponse: any;
  dbImage: string = ''; // Aggiungi questa variabile per contenere l'immagine dal database

  constructor(private userServ: UserService, private atletaServ: AtletaService, private route: ActivatedRoute, private router: Router, private httpClient: HttpClient) { }

  ngOnInit(): void {

    // Recupera l'id dell'atleta dalla rotta parametrica
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.dettagliAtleta();
    });

    // Recupero dell'id dell'utente loggato
  this.userData = this.userServ.getUserData();
  console.log(this.userData.sub);
  this.userId = this.userData.sub;
  }

  // CARICA I DATI DEL SINGOLO ATLETA SAPENDO IL SUO ID
  dettagliAtleta() {
    this.atletaServ.getAtleta(this.id).subscribe(atleta => {
      this.atleta = atleta;
      this.viewImage();
    });
  }

  // Richiama questa funzione per ottenere l'immagine dal database
  viewImage() {
    const headers = new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    const imageId = this.atleta?.immagineProfilo?.id; // Ottieni l'ID dell'immagine
    if (imageId) {
        this.httpClient.get(`http://localhost:3001/get/image/${imageId}`, { responseType: 'blob', headers })
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
}

}
