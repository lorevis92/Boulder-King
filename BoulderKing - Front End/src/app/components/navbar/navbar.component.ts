import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Router, NavigationEnd } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from 'src/app/models/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  currentPage: string = 'home';
  user!: User;
  userData: any;
  userId!: string;
  dbImage: string = '';
  defaultImageURL: string = 'https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png'; // Immagine di default

  constructor(
    private userServ: UserService,
    public authSrv: AuthService,
    private router: Router,
    private httpClient: HttpClient
  ) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const currentRoute = this.router.url;
        if (currentRoute === '/atleta') {
          this.currentPage = 'Kings';
        } else if (currentRoute === '/evento') {
          this.currentPage = 'Gare';
        } else if (currentRoute === '/classifica') {
          this.currentPage = 'Classifiche';
        } else if (currentRoute === '/ente') {
          this.currentPage = 'Palestre';
        } else {
          this.currentPage = 'home';
        }
      }
    });
  }

  ngOnInit(): void {
    this.userData = this.userServ.getUserData();
    console.log(this.userData.sub);
    this.userId = this.userData.sub;
    this.dettagliUser();
  }

  logout() {
    this.authSrv.logout();
    this.router.navigate(['/login']);
    console.log('Logout effettuato con successo');
  }

  dettagliUser() {
    this.userServ.getUser(this.userId).subscribe((user) => {
      this.user = user;
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
  
}
