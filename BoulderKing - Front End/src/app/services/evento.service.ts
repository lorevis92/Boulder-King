import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs';
import { Evento } from '../models/evento.interface';


@Injectable({
  providedIn: 'root'
})
export class EventoService {

  private urleventi = 'http://localhost:3001/eventi';

  constructor(private http: HttpClient) { }

  // LISTA DI TUTTI GLI EVENTI

  getEventi(page:Number, size: Number, order:string): Observable<Evento[]> {
    const params = new HttpParams()
    .set('size', size.toString())
    .set('page', page.toString())
    .set('order', order)
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urleventi, { params, headers })
      .pipe(map(response => response.content));
  }

  getEvento(id : string){
    return this.http.get<Evento>(`${this.urleventi}/${id}`);
  }

  // CREA UN NUOVO EVENTO
  nuovoEvento(
    nomeEvento: string,
    organizzatore: string,
    creatoreEvento: string,
    puntiEvento: string,
    data: string,
    immagineEvento: string,
    citta:string,
    provincia: string,
    regione:string,
    zonaItalia: string,
    info: string,
    sito: string): Observable<any> {
      const newEvento = { nomeEvento, organizzatore, creatoreEvento, puntiEvento, data, citta, provincia, regione, zonaItalia, immagineEvento, info, sito};
    return this.http.post<any>(this.urleventi, newEvento);
  }

  // TROVA EVENTI PER ORGANIZZATORE
  getEventiByOrganizzatore(idOrganizzatore : string | undefined, page:Number, size: Number, order:string): Observable<Evento[]> {
    const params = new HttpParams()
    .set('size', size.toString())
    .set('page', page.toString())
    .set('order', order)
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urleventi + '/organizzatore/' + idOrganizzatore, { params, headers })
      .pipe(map(response => response.content));
  }

  // ISCRIZIONE AD UN EVENTO
  iscrizioneEvento(
    idPartecipante: string, idEvento: string
    ): Observable<any> {
      const iscrizioneEvento = {"idUtente" : idPartecipante};
    return this.http.post<any>(this.urleventi + '/' + idEvento + '/partecipanti' , iscrizioneEvento);
  }

  // Carica Eventi tramite filtri
  searchEventi(filters: any, page: number, size: number,): Observable<Evento[]> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
  
    // Definisci una funzione di utilità per impostare il parametro se è definito in filters
    const setParamIfDefined = (paramName: string, filterValue: string | null) => {
      if (filterValue) {
        params = params.set(paramName, filterValue);
      }
    };
  
    // Usa la funzione di utilità per impostare i parametri
    setParamIfDefined('nomeEvento', filters.nomeEvento);
    setParamIfDefined('nomeEnte', filters.nomeEnte);
    setParamIfDefined('idEnte', filters.idEnte);
    setParamIfDefined('citta', filters.citta);
    setParamIfDefined('provincia', filters.provincia);
    setParamIfDefined('regione', filters.regione);
    setParamIfDefined('zonaItalia', filters.zonaItalia);
    setParamIfDefined('isPassed', filters.isPassed);
  
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
  
    return this.http.get<any>(`${this.urleventi}/search`, { params, headers })
      .pipe(map(response => response.content));
  }  

  updateEvento(
    eventoId: string,
    eventoToUpdate: Evento
  ): Observable<Evento> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    });
  
    return this.http.put<Evento>(`${this.urleventi}/${eventoId}`, eventoToUpdate, {
      headers,
    });
  }

  deleteEvento(
    eventoId: string
  ): Observable<Evento> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    });
  
    return this.http.delete<Evento>(`${this.urleventi}/${eventoId}`, {
      headers,
    });
  }
}
