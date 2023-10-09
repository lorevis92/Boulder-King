import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user.interface';
import { UserService } from 'src/app/services/user.service';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  user!: User;
  userData: any;
  userId!: string;
  postResponse: any;
  dbImage: string = 'https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png'; // Aggiungi questa variabile per contenere l'immagine dal database
  defaultImageURL: string = 'https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png'; // Immagine di default
  coordinate: { lat?: number, lng?: number } = { lat: 0, lng: 0 }; 
  visualizzaModificatore: boolean = false;
  constructor(private userServ: UserService, private route: ActivatedRoute, private router: Router, private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.userData = this.userServ.getUserData();
    console.log(this.userData.sub);
    this.userId = this.userData.sub;
    this.dettagliUser();
  }

  // CARICA I DATI DEL SINGOLO UTENTE SAPENDO IL SUO ID
  dettagliUser() {
    this.userServ.getUser(this.userId).subscribe((user) => {
      this.user = user;
      this.coordinate = { lat: user.latitudine, lng: user.longitudine };
      if (this.user && this.user?.immagineProfilo && this.user?.immagineProfilo?.id !== null && this.user?.immagineProfilo?.id !== undefined) {
        // L'ID dell'immagine è definito e non è né null né undefined
        console.log(this.user?.immagineProfilo?.id);
        this.viewImage();
      } else {
        // Se l'ID dell'immagine è undefined o null, imposta l'immagine di default
        this.dbImage = this.defaultImageURL;
      }
    });
  }

  // Richiama questa funzione per ottenere l'immagine dal database
  viewImage() {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    });
    const imageId = this.user?.immagineProfilo?.id;
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
              // Se si verifica un errore, imposta l'immagine di default
              this.dbImage = this.defaultImageURL;
            }
          );
      } else {
        console.error('ID dell\'immagine non valido.');
        // Se l'ID dell'immagine è vuoto o non valido, imposta l'immagine di default
        this.dbImage = this.defaultImageURL;
      }
    } catch (error) {
      console.error('Errore generale durante il recupero dell\'immagine:', error);
      // In caso di errore generale, imposta l'immagine di default
      this.dbImage = this.defaultImageURL;
    }
  }

  visualizzaAggiornaDati(){
    this.visualizzaModificatore =! this.visualizzaModificatore;
  }
}
