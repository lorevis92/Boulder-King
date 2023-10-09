import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.interface';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AtletaService {

  private urlatleti = 'http://localhost:3001/atleti';
  private urleventi = 'http://localhost:3001/eventi';

  constructor(private http: HttpClient) { }

  // GET PER OTTENERE GLI ATLETI IMPAGINATI
  getAtleti(page:number, order:string): Observable<User[]> {
    const params = new HttpParams()
  
    .set('page', page.toString())
    .set('order', order)
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urlatleti, { params, headers })
      .pipe(map(response => response.content));
  }

  // GET PER OTTENERE GLI ATLETI IMPAGINATI E ORDINATI PER PUNTEGGIO
  getAtletiPerPunteggio(page:number, size: Number, order:string): Observable<User[]> {
    const params = new HttpParams()
  
    .set('page', page.toString())
    .set('size', size.toString())
    .set('order', order)
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urlatleti + '/classifica', { params, headers })
      .pipe(map(response => response.content));
  }
  

  // GET PER OTTENERE UN ATLETA DATO L'ID
  getAtleta(id : string){
    return this.http.get<User>(`${this.urlatleti}/${id}`);
  }

   // GET PER OTTENERE UN ATLETA DATO IL COGNOME
   getAtletaPerCognome(cognome : string, page:number): Observable<User[]> {
    const params = new HttpParams()
    .set('cognome', cognome)
    .set('page', page.toString())
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urlatleti + '/cognome/' + cognome, { params, headers })
      .pipe(map(response => response.content));
  }

  // ATLETI ISCRITTI AD UN EVENTO
  getAtletiPerEvento(id: string, page:Number, pageSize: Number, order:string): Observable<User[]> {
    const params = new HttpParams()
  
    .set('page', page.toString())
    .set('pageSize', pageSize.toString())
    .set('order', order)
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urleventi + '/partecipanti/' + id, { params, headers })
      .pipe(map(response => response.content));
  }

}
