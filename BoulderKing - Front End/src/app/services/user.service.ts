import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.interface';
import { map } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt'; // Se stai usando @auth0/angular-jwt


@Injectable({
  providedIn: 'root',
})
export class UserService {

  private urlUsers = 'http://localhost:3001/users';

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }


  // OTTIENI I DATI DELL'UTENTE CONNESSO
      // Controlla se l'utente è autenticato (il token è valido)
          isAuthenticated(): boolean {
            const token = localStorage.getItem('token'); // Recupera il token dal local storage
            return !this.jwtHelper.isTokenExpired(token);
          }

      // Ottieni i dati dell'utente dal token JWT
      getUserData(): any {
        const token = localStorage.getItem('token');
      
        if (!token) {
          // Il token non è presente, gestisci l'errore in modo appropriato, ad esempio reindirizzando l'utente al login
          return null; // Oppure restituisci un valore predefinito o un oggetto vuoto a seconda delle tue esigenze
        }
      
        try {
          const decodedToken = this.jwtHelper.decodeToken(token);
          console.log('Dati utente decodificati:', decodedToken);
          return decodedToken;
        } catch (error) {
          console.error('Errore durante la decodifica del token:', error);
          return null; // Gestisci l'errore in modo appropriato
        }
      }
      

  // OTTIENI LA LISTA DEGLI UTENTI --> TUTTI GLI ATLETI E GLI ENTI
  getUsers(page:Number, order:string): Observable<User[]> {
    const params = new HttpParams()
  
    .set('page', page.toString())
    .set('order', order)
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urlUsers, { params, headers })
      .pipe(map(response => response.content));
  }

  // GET PER OTTENERE UN USER DATO L'ID
  getUser(id : string){
    return this.http.get<User>(`${this.urlUsers}/${id}`);
  }

  //ModificaUtenti
  updateUser(
    userId: string,
    userToUpdate: User 
  ): Observable<User> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    });
  
    return this.http.put<User>(`${this.urlUsers}/${userId}`, userToUpdate, {
      headers,
    });
  }
  
}
